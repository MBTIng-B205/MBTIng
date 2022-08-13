package com.ssafy.mbting.ws.model.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class OrphanOccurEvent extends ApplicationEvent {

    private final String orphanSessionId;

    public OrphanOccurEvent(
            Object source,
            Clock clock,
            String orphanSessionId) {
        super(source, clock);
        this.orphanSessionId = orphanSessionId;
    }
}
