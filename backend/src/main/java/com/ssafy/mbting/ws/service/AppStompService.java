package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;

import java.util.Optional;

public interface AppStompService {

    void connect(String sessionId, ConnectHeader connectHeader);
    void disconnect(String sessionId);
    void subscribe(String sessionId, MeetingUser meetingUser);
    Optional<StompUser> getStompUserBySessionId(String sessionId);
}
