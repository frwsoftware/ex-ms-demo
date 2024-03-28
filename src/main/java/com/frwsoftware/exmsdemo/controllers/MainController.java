package com.frwsoftware.exmsdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frwsoftware.exmsdemo.clients.WikidataFeignClient;
import com.frwsoftware.exmsdemo.exception.WebConfigurationException;
import com.frwsoftware.exmsdemo.services.FeignClientService;
import com.frwsoftware.exmsdemo.services.WebClientService;
import frwsoftware.model.WikidataResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final FeignClientService feignClientService;
    private final WebClientService webClientService;

    @GetMapping(path = "/test")
    public String test() throws WebConfigurationException {
        log.info("Getting item from Wikidata");
        //feignClientService.getFromWikidata();
        //webClientService.createWebClient("wikidata");
        log.info("Test request");
        return "OK";
    }
    @GetMapping(path = "/errortest")
    public String errortest() throws Exception{
        try {
            if (true) {
                throw new Exception("My ex");
            }
        }
        catch (Exception ex){
            log.error("Test error", ex);
            throw ex;
        }
        return "OK";
    }
}
