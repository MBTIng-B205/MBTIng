package com.ssafy.mbting.ws.model.event.proposal;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class ProposalBothAcceptedEvent extends ApplicationEvent {

    private final String[] sessionIds;
    private final String[] openviduTokens;

    public ProposalBothAcceptedEvent(
            Object source,
            Clock clock,
            String[] sessionIds,
            String[] openviduTokens) {
        super(source, clock);
        this.sessionIds = sessionIds;
        this.openviduTokens = openviduTokens;
    }
}
