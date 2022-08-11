package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.*;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.BaseMessageBody;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.Proposal;
import com.ssafy.mbting.ws.model.vo.IndividualDestination;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
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
import java.util.Arrays;
import java.util.stream.IntStream;

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
        String sessionId = event.getSessionId();
        MeetingUser meetingUser = event.getMeetingUser();

        logger.debug("\n\nRequestToJoin 이벤트 발생함\nSession ID: {}\nMeetingUser: {}\n"
                , sessionId
                , meetingUser);

        waitingMeetingService.subscribe(sessionId, meetingUser);
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

        StompUser stompUser1 = waitingMeetingService.getStompUserBySessionId(sessionId1)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        StompUser stompUser2 = waitingMeetingService.getStompUserBySessionId(sessionId2)
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

    @Async
    @EventListener
    public void onArrive(ProposalResultArriveEvent event) {
        String sessionId = event.getSessionId();
        Boolean accepted = event.getAccepted();

        logger.debug("\n\nProposal Result Arrive 이벤트 발생함\nSession ID: {}\naccepted: {}\n"
                , sessionId
                , accepted ? "수락" : "거절");

        waitingMeetingService.setProposalAcceptedAndHandleIt(
                sessionId,
                accepted);
    }

    @Async
    @EventListener
    public void onMade(ProposalResultsMadeEvent event) {
        String[] sessionIds = event.getSessionIds();
        Boolean[] accepteds = event.getAccepteds();

        logger.debug("\n\nProposal Results Made 이벤트 발생함\n1: ({}, {})\n2: ({}, {})\n"
                , sessionIds[0]
                , accepteds[0] ? "수락" : "거절"
                , sessionIds[1]
                , accepteds[1] ? "수락" : "거절");

        if (accepteds[0] && accepteds[1]) {
            applicationEventPublisher.publishEvent(new ProposalBothAcceptedEvent(
                    this,
                    Clock.systemDefaultZone(),
                    sessionIds,
                    waitingMeetingService
                            .setMeetingRoomAndGetTokensForTwoUsers(sessionIds[0], sessionIds[1])));
            return;
        }

        IntStream.range(0, 2).forEach(i -> {
            if (accepteds[i]) {
                simpMessagingTemplate.convertAndSend(
                        IndividualDestination.of(waitingMeetingService
                                .getStompUserBySessionId(sessionIds[i])
                                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                                .getEmail()).toString(),
                        BaseMessageBody.builder()
                                .command("opponentRefusal")
                                .build());
            } else {
                waitingMeetingService.rejoin(sessionIds[i]);
            }
        });
    }

    @Async
    @EventListener
    public void onBothAccepted(ProposalBothAcceptedEvent event) {
        String[] sessionIds = event.getSessionIds();
        String[] openviduTokens = event.getOpenviduTokens();

        logger.debug("\n\nProposal Both Accepted 이벤트 발생함\nSession IDs: {}\nOpenvidu Tokens: {}\n"
                , sessionIds
                , openviduTokens);

        // Todo: 미팅 입장 정보 제공 메시지 전송
    }
}
