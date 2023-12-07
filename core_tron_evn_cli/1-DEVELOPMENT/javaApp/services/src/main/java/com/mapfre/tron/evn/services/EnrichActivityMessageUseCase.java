package com.mapfre.tron.evn.services;


import com.mapfre.tron.events.dto.ActivityMessage;

public interface EnrichActivityMessageUseCase {

    void handle (ActivityMessage activityMessage);
}
