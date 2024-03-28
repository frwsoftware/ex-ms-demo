package com.frwsoftware.exmsdemo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frwsoftware.exmsdemo.clients.WikidataFeignClient;
import frwsoftware.model.WikidataResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeignClientService {
    private final WikidataFeignClient wikidataFeignClient;

    public void getFromWikidata(){
        String jsonAsString = wikidataFeignClient.getItemFromWikidata();
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            WikidataResp readValue = mapper.readValue(jsonAsString, WikidataResp.class);
            Map<String, Object> add = readValue.getAdditionalProperties();
            log.info("readValue: " + readValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //WikidataResp json = wikidataFeignClient.getJsonItemFromWikidata();
        log.info("xml: " + jsonAsString);

    }
}
