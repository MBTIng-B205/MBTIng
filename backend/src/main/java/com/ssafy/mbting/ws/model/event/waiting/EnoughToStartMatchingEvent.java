package com.ssafy.mbting.ws.model.event.waiting;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class EnoughToStartMatchingEvent extends ApplicationEvent {

    public EnoughToStartMatchingEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
