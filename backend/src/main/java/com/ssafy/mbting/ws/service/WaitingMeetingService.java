package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.stompMessageHeader.StompConnectHeader;
import com.ssafy.mbting.ws.model.stompMessageHeader.StompSubscribeHeader;

public interface WaitingMeetingService {

    void connectUser(StompConnectHeader stompConnectHeader);
    void disconnectUser(String sessionId);
    void takeUser(StompSubscribeHeader stompSubscribeHeader);
    boolean hasSubscribedDestinationBySessionId(String sessionId);
}
