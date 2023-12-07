package com.mapfre.tron.evn.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfiguration {

    @Value("${app.env.kafka.error.handler.interval:1000}")
    private long interval;

    @Value("${app.env.kafka.error.handler.retries:2}")
    private long retries;

    @Bean
    public CommonErrorHandler commonErrorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(new FixedBackOff(interval, retries));
        errorHandler.setResetStateOnRecoveryFailure(false);
        return errorHandler;
    }
}