package com.ssafy.mbting.ws.model.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class ProposalResultResolveEvent extends ApplicationEvent {

    private final String sessionId1;
    private final String sessionId2;
    private final Boolean accepted1;
    private final Boolean accepted2;

    public ProposalResultResolveEvent(
            Object source,
            Clock clock,
            String sessionId1,
            Boolean accepted1,
            String sessionId2,
            Boolean accepted2) {
        super(source, clock);
        this.sessionId1 = sessionId1;
        this.sessionId2 = sessionId2;
        this.accepted1 = accepted1;
        this.accepted2 = accepted2;
    }
}
