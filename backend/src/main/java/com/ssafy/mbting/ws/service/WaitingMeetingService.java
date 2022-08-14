package com.ssafy.mbting.ws.service;

public interface WaitingMeetingService {

    int getQueueSize();
    boolean isEnoughSizeToStartMatching();
    String getFirstSessionId();
    void setMatchedMeetingUsers(String sessionId1, String sessionId2);
    void setProposalAcceptedAndHandleIt(String sessionId, Boolean accepted);
}
