package com.ssafy.mbting.ws.model.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class RequestToLeaveQueueEvent extends ApplicationEvent {
    public RequestToLeaveQueueEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
