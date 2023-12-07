package com.mapfre.tron.evn.infrastructure;

import com.mapfre.tron.events.dto.InsuredMessage;
import com.mapfre.tron.evn.services.InsuredMessageUseCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InsuredEventConsumer {

    private final InsuredMessageUseCase newActivityMessageUseCase;

    public InsuredEventConsumer(InsuredMessageUseCase newActivityMessageUseCase) {
        this.newActivityMessageUseCase = newActivityMessageUseCase;
    }

    @KafkaListener(topics="${app.env.kafka.topic.insured.id}", groupId = "${app.env.kafka.sync.insured.group}", autoStartup = "${app.env.kafka.sync.insured.enabled:true}")
    public void consume(ConsumerRecord<String, InsuredMessage> message)  {
        log.info("Consume offset: " + message.offset() + " key: " + message.key());
        this.newActivityMessageUseCase.handle(message.value());
    }


}
