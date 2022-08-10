package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;

public interface WaitingMeetingUserRepository {

    StompUser createSession(String sessionId, StompUser stompUser);
    void removeSession(String sessionId);
    StompUserStatus getStompUserStatus(String sessionId);
    void saveMeetingUser(String sessionId, MeetingUser meetingUser);
    void joinToQueue(String sessionId);
    void leaveFromQueue(String sessionId);
    void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom);
    StompUser findBySessionId(String sessionId);
    void addSessionIdToFeatureUserTables(String sessionId, MeetingUser meetingUser);
    void removeSessionIdFromFeatureUserTables(String sessionId, MeetingUser meetingUser);
}
