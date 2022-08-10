package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;

public interface WaitingMeetingService {

    void connect(String sessionId, ConnectHeader connectHeader);
    void disconnect(String sessionId);
    void subscribe(String sessionId, MeetingUser meetingUser);
    MeetingUser saveAndGetMatchedMeetingUser(String subjectSessionId, String matchedSessionId);
    String[] getTokensForTwoUsers(String sessionId1, String sessionId2);
    void rejoin(String sessionId);
}
