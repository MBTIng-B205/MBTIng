package com.ssafy.mbting.ws.model.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class WaitingMeetingUserQueuedEvent extends ApplicationEvent {
    public WaitingMeetingUserQueuedEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
