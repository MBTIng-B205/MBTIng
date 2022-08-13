package com.ssafy.mbting.ws.model.event.waiting;

import com.ssafy.mbting.ws.model.vo.MeetingUser;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class RequestToJoinToQueueEvent extends ApplicationEvent {

    private final String sessionId;
    private final MeetingUser meetingUser;

    public RequestToJoinToQueueEvent(Object source, Clock clock, String sessionId, MeetingUser meetingUser) {
        super(source, clock);
        this.sessionId = sessionId;
        this.meetingUser = meetingUser;
    }
}
