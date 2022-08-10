package com.ssafy.mbting.ws.model.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class WaitingMeetingUserQueuedEvent extends ApplicationEvent {

    public WaitingMeetingUserQueuedEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
