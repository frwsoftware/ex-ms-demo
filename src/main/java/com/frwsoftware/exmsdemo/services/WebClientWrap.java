package com.frwsoftware.exmsdemo.services;

import com.frwsoftware.exmsdemo.autoconf.http_clients.HttpClientProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
public class WebClientWrap {
    private WebClient webClient;
    private HttpClientProperties properties;
}
