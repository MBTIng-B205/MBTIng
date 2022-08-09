package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;

public interface WaitingMeetingUserRepository {

    StompUser createSession(String sessionId, StompUser stompUser);
    void removeSession(String sessionId);
    void queueMeetingUser(String sessionId, MeetingUser meetingUser);
    StompUser findBySessionId(String sessionId);
    void addSessionIdToFeatureUserTables(String sessionId, MeetingUser meetingUser);
    void removeSessionIdFromFeatureUserTables(String sessionId, MeetingUser meetingUser);
    int size();
}
