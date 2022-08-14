package com.ssafy.mbting.ws.repository;

import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface AppRepository {

    Map<Gender, Set<String>> getGenderTable();
    Map<String, Set<String>> getSidoTable();
    Map<String, Set<String>> getInterestTable();
    StompUser createSession(String sessionId, StompUser stompUser);
    Optional<StompUser> findStompUserBySessionId(String sessionId);
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
    void setMeetingRoomStatusToFalse(String meetingRoomId, int indexOnRoom);
    void removeMeetingRoom(String meetingRoomId);
    int getQueueSize();
    Optional<String> getFirstSessionId();
}
