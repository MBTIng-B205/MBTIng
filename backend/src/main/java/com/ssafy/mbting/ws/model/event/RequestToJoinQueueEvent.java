package com.ssafy.mbting.ws.model.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class RequestToJoinQueueEvent extends ApplicationEvent {
    public RequestToJoinQueueEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
