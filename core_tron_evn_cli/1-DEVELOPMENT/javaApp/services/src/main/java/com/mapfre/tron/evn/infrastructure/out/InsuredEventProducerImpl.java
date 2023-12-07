package com.mapfre.tron.evn.infrastructure.out;

import com.mapfre.tron.events.dto.InsuredMessage;
import com.mapfre.tron.evn.exceptions.EventWriteException;
import com.mapfre.tron.evn.services.InsuredEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class InsuredEventProducerImpl implements InsuredEventProducer {

    @Value("${app.env.kafka.topic.insured.id}")
    private String topic;

    private final KafkaTemplate<String, InsuredMessage> template;

    public InsuredEventProducerImpl(KafkaTemplate<String, InsuredMessage> template) {
        this.template = template;
    }

    @Override
    public void write(InsuredMessage insuredMessage) {
        ListenableFuture<SendResult<String, InsuredMessage>> future = template.send(topic, insuredMessage.getHeader().getId(), insuredMessage);
        future.addCallback(buildListenableFutureCallback(insuredMessage));
    }

    private ListenableFutureCallback<SendResult<String, InsuredMessage>> buildListenableFutureCallback(InsuredMessage insuredMessage) {
        return new ListenableFutureCallback<SendResult<String, InsuredMessage>>() {

            @Override
            public void onSuccess(SendResult<String, InsuredMessage> result) {
                String message = String.format("SENT message [source:%s][type:%s][id:%s] with offset=[%s] sent to [%s]",
                        insuredMessage.getHeader().getSource(),
                        insuredMessage.getHeader().getType(),
                        insuredMessage.getHeader().getId(),
                        result.getRecordMetadata().offset(),
                        result.getProducerRecord().topic());
                log.info(message);
            }

            @Override
            public void onFailure(Throwable ex) {
                String errorMessage = String.format("ERROR SENDING message [source:%s][type:%s][id:%s] due to : %s",
                        insuredMessage.getHeader().getSource(),
                        insuredMessage.getHeader().getType(),
                        insuredMessage.getHeader().getId(),
                        ex.getMessage());

                log.warn(errorMessage);
                throw new EventWriteException(errorMessage, ex);
            }
        };
    }

}
