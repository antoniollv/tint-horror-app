package com.mapfre.tron.evn.infrastructure;

import com.mapfre.tron.events.dto.ActivityMessage;
import com.mapfre.tron.evn.services.EnrichActivityMessageUseCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActivityEventConsumer {

    private final EnrichActivityMessageUseCase enrichActivityMessageUseCase;

    public ActivityEventConsumer(EnrichActivityMessageUseCase enrichActivityMessageUseCase) {
        this.enrichActivityMessageUseCase = enrichActivityMessageUseCase;
    }

    @KafkaListener(topics="${app.env.kafka.topic.activity.id}", groupId = "${app.env.kafka.enriching.insured.group}", autoStartup = "${app.env.kafka.enriching.insured.enabled:true}")
    public void consume(ConsumerRecord<String, ActivityMessage> message)  {
        log.info("Consume offset: " + message.offset() + " key: " + message.key());
        this.enrichActivityMessageUseCase.handle(message.value());
    }


}
