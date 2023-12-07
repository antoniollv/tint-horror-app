package com.mapfre.tron.evn;

import com.mapfre.tron.evn.commons.TronApiHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    protected RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));
        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(new TronApiHttpMessageConverter());
        return restTemplate;
    }
}
