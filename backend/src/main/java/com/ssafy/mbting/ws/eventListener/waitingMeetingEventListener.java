package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import com.ssafy.mbting.ws.repository.WaitingMeetingUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class waitingMeetingEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher publisher;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WaitingMeetingUserRepository waitingMeetingUserRepository;

    @EventListener
    public void onEnough(WaitingMeetingUserQueueSizeEnoughEvent event) {
        logger.debug("\n\n이거 왔나?????\n");
        if (event.isIntervalEnough()) {
            logger.debug("todo");
        }
    }

    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {
        logger.info("\n\n여기가 마지막\n");
        simpMessagingTemplate.convertAndSend("/ws/sub/indi/" +
                        event.getMeetingUser1().getEmail(),
                "Matched user: " + event.getMeetingUser2());
        simpMessagingTemplate.convertAndSend("/ws/sub/indi/" +
                        event.getMeetingUser2().getEmail(),
                "Matched user: " + event.getMeetingUser1());
    }
}
