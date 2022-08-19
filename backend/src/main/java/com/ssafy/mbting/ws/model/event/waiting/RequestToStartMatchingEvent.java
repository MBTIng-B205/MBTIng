package com.ssafy.mbting.ws.model.event.waiting;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class RequestToStartMatchingEvent extends ApplicationEvent {

    private final int minSize;

    public RequestToStartMatchingEvent(Object source, Clock clock, int minSize) {
        super(source, clock);
        this.minSize = minSize;
    }
}
