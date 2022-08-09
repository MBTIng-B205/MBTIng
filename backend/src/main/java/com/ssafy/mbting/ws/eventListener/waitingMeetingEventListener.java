package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.model.stompMessageBody.TempSuccess;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class waitingMeetingEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void onQueued(WaitingMeetingUserQueuedEvent event) {
        logger.debug("\n\n온큐드 핸들러 왔니?\n");
        simpMessagingTemplate.convertAndSend("/ws/sub/indi/abc", "{\"success\": true}");
    }

    @EventListener
    public void onEnough(WaitingMeetingUserQueueSizeEnoughEvent event) {
    }

    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {
    }
}
