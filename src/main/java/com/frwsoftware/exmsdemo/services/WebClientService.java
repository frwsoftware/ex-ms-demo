package com.frwsoftware.exmsdemo.services;

import com.frwsoftware.exmsdemo.autoconf.http_clients.HttpClientMapProperties;
import com.frwsoftware.exmsdemo.autoconf.http_clients.HttpClientProperties;
import com.frwsoftware.exmsdemo.exception.WebConfigurationException;
import com.frwsoftware.exmsdemo.exception.WebExchageException;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.*;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebClientService {

    private final HttpClientMapProperties httpClientMap;
    private ReactiveClientRegistrationRepository clientRegistrations;

    public WebClientWrap createWebClient(String id) throws WebConfigurationException {
        return createWebClient(id, null);
    }
    public WebClientWrap createOAuthWebClient(String id) throws WebConfigurationException {
        return createQAuthWebClientLocal(id);
    }

    private WebClientWrap createWebClient(String id, ExchangeFilterFunction filter) throws WebConfigurationException {
        WebClient webClient = null;
        if (filter != null)
            webClient = webClientBuilder(id).filter(filter).build();
        else
            webClient = webClientBuilder(id).build();

        HttpClientProperties props = httpClientMap.getHttpClients().get(id);
        WebClientWrap webClientWrap = new WebClientWrap();
        webClientWrap.setWebClient(webClient);
        webClientWrap.setProperties(props);
        return webClientWrap;
    }
    private WebClient.Builder webClientBuilder(String id) throws WebConfigurationException {
        HttpClientProperties props = httpClientMap.getHttpClients().get(id);
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(props.getMaxInMemorySize()))
                .build()).baseUrl(props.getBaseUrl()).clientConnector(new ReactorClientHttpConnector(
                createHttpClient(props)
            ));
    }

    private HttpClient createHttpClient(HttpClientProperties props) throws WebConfigurationException {
        HttpClient client = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,  props.getTimeOut() * 1000)
                .responseTimeout(Duration.ofSeconds(props.getTimeOut()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(props.getTimeOut(), TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(props.getTimeOut(), TimeUnit.SECONDS))
        );
        boolean isHttps = false;
        if (props.getBaseUrl().startsWith("https")) isHttps = true;
        if (isHttps) {
            try {
                SslContext sslContext = SslContextBuilder.forClient().build();
                client.secure(
                        sslContextSpec -> sslContextSpec.sslContext(sslContext)
                );
            } catch (SSLException e) {
                throw new WebConfigurationException(e);
            }
        }
        return client;
    }

    
    private WebClientWrap createQAuthWebClientLocal(String clientRegistrationId) throws WebConfigurationException {
        if(isNull(clientRegistrations))  {
            createOAuth2ClientRepository();
        }
        InMemoryReactiveOAuth2AuthorizedClientService clientService =
                new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        return createWebClient(clientRegistrationId, oauth);
    }

    private void createOAuth2ClientRepository() {
        List<ClientRegistration> registrations = new ArrayList();
        for (var hp : httpClientMap.getHttpClients().entrySet()){
            registrations.add(ClientRegistration.withRegistrationId(hp.getKey())
                    .tokenUri(hp.getValue().getTokenUri())
                    .clientId(hp.getValue().getClientId())
                    .clientSecret(hp.getValue().getClientSecret())
                    .authorizationGrantType(new AuthorizationGrantType(hp.getValue().getAuthorizationGrantType()))
                    .build());
        }
        clientRegistrations = new InMemoryReactiveClientRegistrationRepository(registrations);
    }

    /////

    public ResponseEntity<String> send(WebClientWrap webClientWrap,
                                       Map<String, String> reqHeaders,
                                       String reqData,
                                       String uri) throws WebExchageException {
        String fullUrl = Path.of(webClientWrap.getProperties().getBaseUrl()).resolve(uri).toUri().toString();
        try {
            ResponseEntity<String> response = webClientWrap.getWebClient()
                    .post()
                    .uri(uri)
                    .headers(httpHeaders -> reqHeaders.forEach(httpHeaders::add))
                    .bodyValue(reqData)
                    .retrieve()
                    .toEntity(String.class)
                    .block();
            if (nonNull(response) && response.getStatusCode().value() > 399){
                //error
            }
            log.info("Send to {}", fullUrl);
            log.info("Http response status: {}, response: {}", response.getStatusCode().value(), response);
            return response;
        } catch (WebClientRequestException e) {
            throw new WebExchageException("Exception while send to " + fullUrl, e);//, reqHeaders, reqData);
        } catch (WebClientResponseException e) {
            throw new WebExchageException("Exception while send to " + fullUrl, e);//, reqHeaders, reqData);
            //throw new WebExchageException(e.getResponseBodyAsString(), e.getStatusCode().toString(), fullUrl, reqHeaders, reqData);
        } catch (Exception e) {
            throw new WebExchageException("Exception while send to " + fullUrl, e);//, reqHeaders, reqData);
            //throw new WebExchageException("Exception while send to " + fullUrl, e, reqHeaders, reqData);
        }
    }

}
