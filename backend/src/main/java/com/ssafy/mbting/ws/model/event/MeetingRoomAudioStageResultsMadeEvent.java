package com.ssafy.mbting.ws.model.event;

import com.ssafy.mbting.api.request.AudioStageResult;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class MeetingRoomAudioStageResultsMadeEvent extends ApplicationEvent {

    private final String[] sessionIds;
    private final AudioStageResult[] voiceResults;

    public MeetingRoomAudioStageResultsMadeEvent(
            Object source,
            Clock clock,
            String sessionId1,
            AudioStageResult voiceResult1,
            String sessionId2,
            AudioStageResult voiceResult2) {
        super(source, clock);
        sessionIds = new String[] {sessionId1, sessionId2};
        voiceResults = new AudioStageResult[] {voiceResult1, voiceResult2};
    }
}
