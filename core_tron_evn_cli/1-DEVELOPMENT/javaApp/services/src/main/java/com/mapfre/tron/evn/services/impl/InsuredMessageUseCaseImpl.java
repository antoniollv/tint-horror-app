package com.mapfre.tron.evn.services.impl;

import com.mapfre.tron.events.dto.InsuredMessage;
import com.mapfre.tron.events.dto.data.insured.Insured;
import com.mapfre.tron.evn.services.InsuredMessageUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InsuredMessageUseCaseImpl implements InsuredMessageUseCase {

    @Value("${app.env.description}")
    private String environment;

    public InsuredMessageUseCaseImpl() {
    }

    @Override
    public void handle(InsuredMessage insuredMessage) {

        boolean sameOrigin = insuredMessage.getHeader().getSource().startsWith(environment);

        Insured data = insuredMessage.getData();

        // do sync
    }
}
