package com.mapfre.tron.sfv.pgm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
public class RestTemplateApiConfig {
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
