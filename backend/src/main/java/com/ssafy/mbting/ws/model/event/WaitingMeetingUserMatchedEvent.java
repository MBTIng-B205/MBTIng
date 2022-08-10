package com.ssafy.mbting.ws.model.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class WaitingMeetingUserMatchedEvent extends ApplicationEvent {

    private final String sessionId1;
    private final String sessionId2;

    public WaitingMeetingUserMatchedEvent(Object source, Clock clock,
                                          String sessionId1, String sessionId2) {
        super(source, clock);
        this.sessionId1 = sessionId1;
        this.sessionId2 = sessionId2;
    }
}
