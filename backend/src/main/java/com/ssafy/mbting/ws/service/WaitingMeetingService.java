package com.ssafy.mbting.ws.service;

public interface WaitingMeetingService {

    int getQueueSize();
    String getFirstSessionId();
    void setMatchedMeetingUsers(String sessionId1, String sessionId2);
    String[] setMeetingRoomAndGetTokensForTwoUsers(String sessionId1, String sessionId2);
}
