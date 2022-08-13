package com.ssafy.mbting.ws.model.event.room;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class AddFriendEvent extends ApplicationEvent {

    private final String sessionId;

    public AddFriendEvent(Object source, Clock clock, String sessionId) {
        super(source, clock);
        this.sessionId = sessionId;
    }
}
