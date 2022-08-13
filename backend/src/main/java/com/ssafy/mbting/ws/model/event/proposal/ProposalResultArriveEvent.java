package com.ssafy.mbting.ws.model.event.proposal;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class ProposalResultArriveEvent extends ApplicationEvent {

    private final String sessionId;
    private final Boolean accepted;

    public ProposalResultArriveEvent(Object source, Clock clock, String sessionId, Boolean accepted) {
        super(source, clock);
        this.sessionId = sessionId;
        this.accepted = accepted;
    }
}
