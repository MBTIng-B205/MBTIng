package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.stompMessageHeader.SubscribeHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import org.springframework.messaging.MessageChannel;

public interface WaitingMeetingService {

    void connectUser(String sessionId, ConnectHeader connectHeader);
    void disconnectUser(String sessionId);
    void takeUser(String sessionId, MeetingUser meetingUser);
    boolean hasSubscribedDestinationBySessionId(String sessionId);
}
