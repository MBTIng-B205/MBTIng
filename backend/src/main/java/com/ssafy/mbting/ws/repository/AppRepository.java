package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;

import java.util.Optional;

public interface AppRepository {

    StompUser createSession(String sessionId, StompUser stompUser);
    void removeSession(String sessionId);
    void saveMeetingUser(String sessionId, MeetingUser meetingUser);
    void joinToQueue(String sessionId);
    void leaveFromQueue(String sessionId);
    void setMatchedMeetingUser(String subjectSessionId, String matchedSessionId);
    void setProposalAccepted(String sessionId, Boolean accepted);
    Optional<MeetingRoom> findMeetingRoomByMeetingRoomId(String meetingRoomId);
    void setVoiceResult(String sessionId, AudioStageResult greenLight);
    void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom);
    void setMeetingRoomIdAndIndex(String sessionId, String meetingRoomId, Integer indexOnRoom);
    Optional<StompUser> findStompUserBySessionId(String sessionId);
    int getQueueSize();
    Optional<String> getFirstSessionId();
}
