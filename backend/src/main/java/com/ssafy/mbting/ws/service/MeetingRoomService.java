package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.api.request.AudioStageResult;

public interface MeetingRoomService {

    void setProposalAcceptedAndHandleIt(String sessionId, Boolean accepted);
    void setVoiceResultAndHandleIt(String sessionId, AudioStageResult voiceResult);
}
