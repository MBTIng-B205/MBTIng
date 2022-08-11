package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;

import java.util.Optional;

public interface WaitingMeetingUserRepository {

    StompUser createSession(String sessionId, StompUser stompUser);
    void removeSession(String sessionId);
    void saveMeetingUser(String sessionId, MeetingUser meetingUser);
    void joinToQueue(String sessionId);
    void leaveFromQueue(String sessionId);
    void setMatchedMeetingUser(String subjectSessionId, String matchedSessionId);
    void setProposalAccepted(String sessionId, Boolean accepted);
    void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom);
    void setMeetingRoomIdAndIndex(String sessionId, String meetingRoomId, Integer indexOnRoom);
    Optional<StompUser> findBySessionId(String sessionId);
    int getQueueSize();
    Optional<String> getFirstSessionId();
}
