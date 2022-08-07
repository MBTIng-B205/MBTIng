package com.ssafy.mbting.ws.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class WaitingMeetingUserEnoughEvent extends ApplicationEvent {

    private static final long ENOUGH_INTERVAL_IN_SECOND = 3;
    private static Instant previous = Instant.ofEpochMilli(0);

    public WaitingMeetingUserEnoughEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public boolean isIntervalEnough() {
        Instant now = Instant.now(Clock.systemDefaultZone());
        long interval = Duration.between(previous, now).getSeconds();
        if (interval > ENOUGH_INTERVAL_IN_SECOND) {
            previous = now;
            return true;
        }
        return false;
    }
}
