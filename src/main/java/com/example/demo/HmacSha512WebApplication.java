package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class HmacSha512WebApplication extends SpringBootServletInitializer {

	/**
	 * log4j.Logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("Logger");

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(HmacSha512WebApplication.class);
	}

	public static void main(String[] args) {
		LOGGER.info("Before - App Started --------");
		SpringApplication.run(HmacSha512WebApplication.class, args);
		LOGGER.info("After - App Started --------");
	}

	/**
	 * method to declare logger bean to bind with the spring application.
	 *
	 * @return org.slf4j.Logger
	 */
	@Bean(name = "LOGGER")
	public Logger getLOGGER() {
		return LOGGER;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
