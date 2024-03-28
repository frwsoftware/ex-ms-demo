package com.frwsoftware.exmsdemo.clients;

import frwsoftware.model.WikidataResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "wikidataclient", url = "${wikidata.url}entities/items/Q37985", configuration = FeignConfig.class)
public interface WikidataFeignClient {
    @GetMapping
    String getItemFromWikidata();
    @GetMapping
    WikidataResp getJsonItemFromWikidata();
}
