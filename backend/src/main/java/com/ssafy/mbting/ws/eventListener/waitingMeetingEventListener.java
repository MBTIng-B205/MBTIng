package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class waitingMeetingEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void onEnough(WaitingMeetingUserQueueSizeEnoughEvent event) {
    }

    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {
    }
}
