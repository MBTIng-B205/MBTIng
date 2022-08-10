package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.RequestToJoinQueueEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class waitingMeetingEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WaitingMeetingService waitingMeetingService;

    @EventListener
    public void onRequestToJoin(RequestToJoinQueueEvent event) {
        logger.debug("\n\nonRequestToJoin 이벤트 발생함\n");
        waitingMeetingService.subscribe(event.getSessionId(), event.getMeetingUser());
    }

    @EventListener
    public void onQueued(WaitingMeetingUserQueuedEvent event) {
        logger.debug("\n\n온큐드 핸들러 왔니?\n");
        simpMessagingTemplate.convertAndSend("/ws/sub/indi/abc", "{\"success\": \"진짜 이게 옴?\"}");
    }

    @EventListener
    public void onEnough(WaitingMeetingUserQueueSizeEnoughEvent event) {
    }

    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {
    }
}
