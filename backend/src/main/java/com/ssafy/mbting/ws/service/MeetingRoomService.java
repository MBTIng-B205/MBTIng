package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.api.request.AudioStageResult;

public interface MeetingRoomService {

    String[] setMeetingRoomAndGetTokensForTwoUsers(String sessionId1, String sessionId2);
    void leaveFromMeetingRoomAndRemoveIfEmpty(String sessionId);
    void setVoiceResultAndHandleIt(String sessionId, AudioStageResult voiceResult);
    void setVideoStageStartTime(String meetingRoomId);
    void setEndTimeIfAbsent(String meetingRoomId);
    void setFriendResult(String sessionId, Boolean AddOrRemove);
}
