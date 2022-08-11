package com.ssafy.mbting.ws.model.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ProposalResultArriveEvent extends ApplicationEvent {

    private final String email;
    private final Boolean accepted;

    public ProposalResultArriveEvent(Object source, Clock clock, String email, Boolean accepted) {
        super(source, clock);
        this.email = email;
        this.accepted = accepted;
    }
}
