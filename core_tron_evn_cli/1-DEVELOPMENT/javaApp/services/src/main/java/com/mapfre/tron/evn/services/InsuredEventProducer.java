package com.mapfre.tron.evn.services;


import com.mapfre.tron.events.dto.InsuredMessage;

public interface InsuredEventProducer {

    void write (InsuredMessage insuredMessage);
}
