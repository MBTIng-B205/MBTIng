package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;

import java.util.Optional;

public interface WaitingMeetingService {

    void connect(String sessionId, ConnectHeader connectHeader);
    void disconnect(String sessionId);
    void subscribe(String sessionId, MeetingUser meetingUser);
    void setMatchedMeetingUsers(String sessionId1, String sessionId2);
    String[] setMeetingRoomAndGetTokensForTwoUsers(String sessionId1, String sessionId2);
    void rejoin(String sessionId);
    int getQueueSize();
    String getFirstSessionId();
    Optional<StompUser> getStompUserBySessionId(String sessionId);
    void setProposalAcceptedAndHandleIt(String sessionId, Boolean accepted);
}
