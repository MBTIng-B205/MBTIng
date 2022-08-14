package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.waiting.RequestToJoinToQueueEvent;
import com.ssafy.mbting.ws.model.event.waiting.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.waiting.EnoughToStartMatchingEvent;
import com.ssafy.mbting.ws.model.event.waiting.RequestToStartMatchingEvent;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.BaseMessageBody;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.Proposal;
import com.ssafy.mbting.ws.model.vo.IndividualDestination;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.service.AppStompService;
import com.ssafy.mbting.ws.service.MeetingMatchService;
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
public class MeetingMatchEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AppStompService appStompService;
    private final WaitingMeetingService waitingMeetingService;
    private final MeetingMatchService meetingMatchService;

    @Async
    @EventListener
    public void onRequestToJoinToQueue(RequestToJoinToQueueEvent event) {
        String sessionId = event.getSessionId();
        MeetingUser meetingUser = event.getMeetingUser();

        logger.debug("\n\nRequestToJoin 이벤트 발생함\nSession ID: {}\nMeetingUser: {}\n"
                , sessionId
                , meetingUser);

        appStompService.subscribe(sessionId, meetingUser);
    }

    @Async
    @EventListener
    public void onRequestToStartMatching(RequestToStartMatchingEvent event) {
        logger.debug("\n\n매칭 시작 요청 이벤트 발생함\n이벤트 소스: {}\n", event.getSource());

        if (!waitingMeetingService.isEnoughSizeToStartMatching()) return;

        applicationEventPublisher.publishEvent(new EnoughToStartMatchingEvent(
                this,
                Clock.systemDefaultZone()
        ));
    }

    @Async
    @EventListener
    public void onEnoughToStartMatching(EnoughToStartMatchingEvent event) {
        logger.debug("\n\nEnough 이벤트 발생함\n");

        meetingMatchService.startMatching();
    }

    @Async
    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {

        String sessionId1 = event.getSessionId1();
        String sessionId2 = event.getSessionId2();
        logger.debug("\n\nMatched 이벤트 발생함\n({}, {})\n", sessionId1, sessionId2);

        waitingMeetingService.setMatchedMeetingUsers(sessionId1, sessionId2);

        StompUser stompUser1 = appStompService.getStompUserBySessionId(sessionId1)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        StompUser stompUser2 = appStompService.getStompUserBySessionId(sessionId2)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));

        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(stompUser1.getEmail()).toString(),
                BaseMessageBody.builder()
                        .command("proposal")
                        .data(Proposal.of(stompUser2.getMeetingUser()))
                        .build());
        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(stompUser2.getEmail()).toString(),
                BaseMessageBody.builder()
                        .command("proposal")
                        .data(Proposal.of(stompUser1.getMeetingUser()))
                        .build());
    }
}
