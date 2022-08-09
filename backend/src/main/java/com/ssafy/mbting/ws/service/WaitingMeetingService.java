package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.stompMessageHeader.SubscribeHeader;

public interface WaitingMeetingService {

    void connectUser(String sessionId, ConnectHeader connectHeader);
    void disconnectUser(String sessionId);
    void takeUser(String sessionId, SubscribeHeader subscribeHeader);
    boolean hasSubscribedDestinationBySessionId(String sessionId);
}
