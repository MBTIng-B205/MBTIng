package com.ssafy.mbting.ws.model.event.waiting;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class RequestToStartMatchingEvent extends ApplicationEvent {

    public RequestToStartMatchingEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
