package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;

public interface WaitingMeetingUserRepository {

    void createSession(String sessionId, StompUser stompUser);
    void removeSession(String sessionId);
    void takeUser(MeetingUser meetingUser);
    boolean hasSubscribedDestinationBySessionId(String sessionId);
    MeetingUser findByEmail(String email);
    String peekMeetingUserEmail();
    int size();
    boolean hasEnoughSize();
}
