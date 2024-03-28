package com.frwsoftware.exmsdemo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//https://dzone.com/articles/configuring-graceful-shutdown-readiness-and-livene
@RestController
@Slf4j
public class LivenessExampleController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public LivenessExampleController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/complete-normally")
    public String completeNormally() throws Exception {
        return "Hello from Controller";
    }

    @GetMapping("/i-will-sleep-for-30sec")
    public String destroy() throws Exception {
        log.info("------------------ Sleeping for 30 sec");
        Thread.sleep(30000);
        return "sleep complete";
    }

    @GetMapping("/readiness/accepting")
    public String markReadinesAcceptingTraffic() {
        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);
        return "Readiness marked as ACCEPTING_TRAFFIC";
    }

    @GetMapping("/readiness/refuse")
    public String markReadinesRefusingTraffic() {
        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC);
        return "Readiness marked as REFUSING_TRAFFIC";
    }

    @GetMapping("/liveness/correct")
    public String markLivenessCorrect() {
        AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.CORRECT);
        return "Liveness marked as CORRECT";
    }

    @GetMapping("/liveness/broken")
    public String markLivenessBroken() {
        AvailabilityChangeEvent.publish(eventPublisher, this, LivenessState.BROKEN);
        return "Liveness marked as BROKEN";
    }



    @GetMapping("/out-of-memory-error")
    public String OutOfMemoryError() throws Exception {

        List<byte[]> list = new ArrayList<>();
        int index = 1;
        while (true) {
            // 1MB each loop, 1 x 1024 x 1024 = 1048576
            byte[] b = new byte[1048576];
            list.add(b);
            Runtime rt = Runtime.getRuntime();
            log.info("{} free memory: {}", index++, rt.freeMemory());
        }
        //return "OutOfMemoryError";
    }
    @GetMapping("/out-of-memory-error2")
    public String OutOfMemoryError2() throws Exception {
        int dummyArraySize = 15;
        log.info("Max JVM memory: " + Runtime.getRuntime().maxMemory());
        long memoryConsumed = 0;
        try {
            long[] memoryAllocated = null;
            for (int loop = 0; loop < Integer.MAX_VALUE; loop++) {
                memoryAllocated = new long[dummyArraySize];
                memoryAllocated[0] = 0;
                memoryConsumed += dummyArraySize * Long.SIZE;
                log.info("Memory Consumed till now: " + memoryConsumed);
                dummyArraySize *= dummyArraySize * 2;
                Thread.sleep(500);
            }
        } catch (OutOfMemoryError outofMemory) {
            log.info("Catching out of memory error");
            //Log the information,so that we can generate the statistics (latter on).
            throw outofMemory;
        }
        return "OK";
    }
    @GetMapping("/stack-overflow-error")
    public String StackOverflowError() throws Exception {
        printNumber(0);
        return "OK";
    }
    // Method to print numbers
    public static int printNumber(int i)
    {
        i = i + 2;
        System.out.println(i);
        return i + printNumber(i + 2);
    }

}
