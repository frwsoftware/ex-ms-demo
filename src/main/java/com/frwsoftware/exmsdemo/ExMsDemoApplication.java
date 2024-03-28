package com.frwsoftware.exmsdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
//@EnableCaching
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
//public class ExMsDemoApplication {
public class ExMsDemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExMsDemoApplication.class, args);
	}

	@Autowired
	private Environment environment;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		String mode = getArgFirstValue(args, "mode", null);
		if (mode != null){
			log.debug("Starting with mode " + mode);
		}
		String[] activeProfiles = environment.getActiveProfiles();
		log.info("active profiles: {}", Arrays.toString(activeProfiles));
	}

	private String getArgFirstValue(ApplicationArguments args, String name, String defaultValue){
		if (args.containsOption(name) && !args.getOptionValues(name).isEmpty())
			return args.getOptionValues(name).get(0);
		else return defaultValue;
	}
	private String[] getArgArrayValue(ApplicationArguments args, String name, String[] defaultValue){
		if (args.containsOption(name) && !args.getOptionValues(name).isEmpty())
			return args.getOptionValues(name).toArray(String[]::new);
		else return defaultValue;
	}
}
