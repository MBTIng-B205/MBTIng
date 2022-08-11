package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.WsDestination;
import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MeetingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OpenviduService openviduService ;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WaitingMeetingService waitingMeetingService;
    @MessageMapping("/indi/proposalResult")
    public void receiveProposalResult(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId);
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                WsDestination.of(email).getDestination(),
                "{\"command\":\"accept\",\"data\":{\"token\":\"someTokTok\"}}");
    }

    @MessageMapping("/indi/rejoin")
    public void receiveRejoin(@Payload Message message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId);
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                WsDestination.of(email).getDestination(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }
    @MessageMapping("/indi/meetingAudioStarted")
    public void receiveAudioStarted(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId);
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                WsDestination.of(email).getDestination(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }


    @MessageMapping("/indi/greenlight")
    public void receiveGreenLight(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId);
        String email = stompUser.getEmail();
        simpMessagingTemplate.convertAndSend(
                WsDestination.of(email).getDestination(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }

    @MessageMapping("/indi/redlight")
    public void receiveRedLight(@Payload Message<String> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        String sessionId = header.getSessionId();
        StompUser stompUser = waitingMeetingService.getStompUserBySessionId(sessionId);
        String email = stompUser.getEmail();

        simpMessagingTemplate.convertAndSend(
                WsDestination.of(email).getDestination(),
                "{\"command\":\"test\",\"data\":{\"token\":\"someTokTok\"}}");
    }
}
