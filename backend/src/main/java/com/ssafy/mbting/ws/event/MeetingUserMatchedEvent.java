package com.ssafy.mbting.ws.event;

import com.ssafy.mbting.ws.model.MeetingUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class MeetingUserMatchedEvent extends ApplicationEvent {

    private final MeetingUser meetingUser1;
    private final MeetingUser meetingUser2;

    public MeetingUserMatchedEvent(Object source, Clock clock, MeetingUser meetingUser1, MeetingUser meetingUser2) {
        super(source, clock);
        this.meetingUser1 = meetingUser1;
        this.meetingUser2 = meetingUser2;
    }
}
