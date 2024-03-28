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
@ConfigurationProperties("http-clients")
public class HttpClientMapProperties {
    private Map<String, HttpClientProperties> httpClients;
    private String test;
}
