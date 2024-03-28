package com.frwsoftware.exmsdemo.autoconf.http_clients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@ToString
@Configuration
public class HttpClientProperties {

    private String baseUrl;
    private Integer timeOut = 60;//sec
    private Integer maxInMemorySize = 20 * 1024 * 1024;
    private String tokenUri;
    private String clientId;
    private String clientSecret;
    private String authorizationGrantType;

}
