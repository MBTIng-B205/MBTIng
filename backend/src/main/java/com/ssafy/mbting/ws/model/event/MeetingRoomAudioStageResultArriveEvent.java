package com.ssafy.mbting.ws.model.event;

import com.ssafy.mbting.api.request.AudioStageResult;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class MeetingRoomAudioStageResultArriveEvent extends ApplicationEvent {

    private final String sessionId;
    private final AudioStageResult voiceResult;

    public MeetingRoomAudioStageResultArriveEvent(
            Object source,
            Clock clock,
            String sessionId,
            AudioStageResult voiceResult) {
        super(source, clock);
        this.sessionId = sessionId;
        this.voiceResult = voiceResult;
    }
}
