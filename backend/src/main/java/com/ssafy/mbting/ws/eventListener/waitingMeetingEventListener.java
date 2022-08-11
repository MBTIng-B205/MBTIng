package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.RequestToJoinQueueEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.BaseMessageBody;
import com.ssafy.mbting.ws.model.vo.IndividualDestination;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
@RequiredArgsConstructor
public class waitingMeetingEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WaitingMeetingService waitingMeetingService;

    @Async
    @EventListener
    public void onRequestToJoin(RequestToJoinQueueEvent event) {
        logger.debug("\n\nRequestToJoin 이벤트 발생함\n");
        waitingMeetingService.subscribe(event.getSessionId(), event.getMeetingUser());
    }

    @Async
    @EventListener
    public void onQueued(WaitingMeetingUserQueuedEvent event) {
        logger.debug("\n\nQueued 이벤트 발생함\n");
        // Todo: 매치 시작 트리거 조건 체크...

        // 임시로 세 명 오면 시작
        if (waitingMeetingService.getQueueSize() < 3) return;

        applicationEventPublisher.publishEvent(new WaitingMeetingUserQueueSizeEnoughEvent(
                this,
                Clock.systemDefaultZone()
        ));
    }

    @Async
    @EventListener
    public void onEnough(WaitingMeetingUserQueueSizeEnoughEvent event) {
        logger.debug("\n\nEnough 이벤트 발생함\n");
        // Todo: 매칭 알고리즘 발동...

        //임시로 먼저 온 두 명을 무조건 꺼냄
        String sessionId1 = waitingMeetingService.getFirstSessionId();
        String sessionId2 = waitingMeetingService.getFirstSessionId();

        applicationEventPublisher.publishEvent(new WaitingMeetingUserMatchedEvent(
                this,
                Clock.systemDefaultZone(),
                sessionId1,
                sessionId2
        ));
    }

    @Async
    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {

        String sessionId1 = event.getSessionId1();
        String sessionId2 = event.getSessionId2();
        logger.debug("\n\nMatched 이벤트 발생함\n({}, {})\n", sessionId1, sessionId2);

        waitingMeetingService.setMatchedMeetingUsers(sessionId1, sessionId2);

        StompUser stompUser1 = waitingMeetingService.getStompUserBySessionId(sessionId1);
        StompUser stompUser2 = waitingMeetingService.getStompUserBySessionId(sessionId2);

        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(stompUser1.getEmail()).toString(),
                BaseMessageBody.builder()
                        .command("proposal")
                        // Todo: Proposal 타입으로 of 하기
                        .data(stompUser2.getMeetingUser())
                        .build());
        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(stompUser2.getEmail()).toString(),
                BaseMessageBody.builder()
                        .command("proposal")
                        // Todo: Proposal 타입으로 of 하기
                        .data(stompUser1.getMeetingUser())
                        .build());
    }
}
