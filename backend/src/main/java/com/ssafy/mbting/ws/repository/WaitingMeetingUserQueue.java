package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.ws.model.vo.MeetingUser;

public interface WaitingMeetingUserQueue {

    void createSession(String sessionId, String email);
    void removeSession(String sessionId);
    void takeUser(MeetingUser meetingUser);
    boolean hasSubscribedDestinationBySessionId(String sessionId);
    MeetingUser findByEmail(String email);
    String peekMeetingUserEmail();
    int size();
    boolean hasEnoughSize();
}
