package com.ssafy.mbting.ws.model.event.proposal;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class ProposalResultsMadeEvent extends ApplicationEvent {

    private final String[] sessionIds;
    private final Boolean[] accepteds;

    public ProposalResultsMadeEvent(
            Object source,
            Clock clock,
            String sessionId1,
            Boolean accepted1,
            String sessionId2,
            Boolean accepted2) {
        super(source, clock);
        sessionIds = new String[]{sessionId1, sessionId2};
        accepteds = new Boolean[]{accepted1, accepted2};
    }
}
