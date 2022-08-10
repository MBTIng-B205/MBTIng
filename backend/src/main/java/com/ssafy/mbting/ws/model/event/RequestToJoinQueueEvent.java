package com.ssafy.mbting.ws.model.event;

import com.ssafy.mbting.ws.model.vo.MeetingUser;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class RequestToJoinQueueEvent extends ApplicationEvent {

    private final String sessionId;
    private final MeetingUser meetingUser;

    public RequestToJoinQueueEvent(Object source, Clock clock, String sessionId, MeetingUser meetingUser) {
        super(source, clock);
        this.sessionId = sessionId;
        this.meetingUser = meetingUser;
    }
}
