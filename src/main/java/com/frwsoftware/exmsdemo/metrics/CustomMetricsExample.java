package com.frwsoftware.exmsdemo.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class CustomMetricsExample {
    private AtomicLong summer;

    @PostConstruct
    public void preLoad() throws InterruptedException {
        log.info("------------------ preLoad Sleeping for 30 sec");
        //Thread.sleep(30000);
        log.info("------------------ preLoad OK");
    }

    public CustomMetricsExample(MeterRegistry meterRegistry) {
        summer = new AtomicLong();
        meterRegistry.gauge("summer", summer);
    }

    @Scheduled(fixedDelay = 5000)
    public void changeSummer(){
        summer.set((new Date()).getSeconds());
    }
}
