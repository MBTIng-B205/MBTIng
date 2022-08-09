package com.ssafy.mbting.ws.model.event;

import com.ssafy.mbting.ws.model.vo.StompUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class WaitingMeetingUserQueuedEvent extends ApplicationEvent {

    private final String destination;

    public WaitingMeetingUserQueuedEvent(Object source, Clock clock, StompUser stompUser) {
        super(source, clock);
        destination = "/ws/sub/indi/" + stompUser.getEmail();
    }
}
