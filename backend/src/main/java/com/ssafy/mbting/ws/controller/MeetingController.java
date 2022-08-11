package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.ws.model.event.ProposalResultArriveEvent;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.IndividualDestination;
import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.Clock;

@Controller
@RequiredArgsConstructor
public class MeetingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OpenviduService openviduService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WaitingMeetingService waitingMeetingService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @MessageMapping("/indi/proposalResult")
    public void receiveProposalResult(@Payload Message<Boolean> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String email = stompUser.getEmail();
        applicationEventPublisher.publishEvent(new ProposalResultArriveEvent(
                this,
                Clock.systemDefaultZone(),
                email,
                message.getPayload()));
    }

    @MessageMapping("/indi/rejoin")
    public void receiveRejoin(@Payload Message message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(email).toString(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }

    @MessageMapping("/indi/meetingAudioStarted")
    public void receiveAudioStarted(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(email).toString(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }


    @MessageMapping("/indi/greenlight")
    public void receiveGreenLight(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(email).toString(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }

    @MessageMapping("/indi/redlight")
    public void receiveRedLight(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String email = stompUser.getEmail();

        simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(email).toString(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }
}
