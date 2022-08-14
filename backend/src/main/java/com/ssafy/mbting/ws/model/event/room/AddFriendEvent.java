package com.ssafy.mbting.ws.model.event.room;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class AddFriendEvent extends ApplicationEvent {

    private final String sessionId;
    private final Boolean addOrRemove;

    public AddFriendEvent(
            Object source,
            Clock clock,
            String sessionId,
            Boolean addOrRemove) {
        super(source, clock);
        this.sessionId = sessionId;
        this.addOrRemove = addOrRemove;
    }
}
