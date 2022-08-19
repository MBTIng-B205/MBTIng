package com.ssafy.mbting.ws.model.event.room;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class MeetingRoomAudioStageBothGreenEvent extends ApplicationEvent {

    private final String meetingRoomId;
    private final String[] sessionIds;

    public MeetingRoomAudioStageBothGreenEvent(
            Object source,
            Clock clock,
            String meetingRoomId,
            String[] sessionIds) {
        super(source, clock);
        this.meetingRoomId = meetingRoomId;
        this.sessionIds = sessionIds;
    }
}
