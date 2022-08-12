package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.ws.model.event.ProposalResultArriveEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.Clock;

@Controller
@RequiredArgsConstructor
public class WaitingMeetingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;

    @MessageMapping("/indi/proposalResult")
    public void receiveProposalResult(@Payload Message<Boolean> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        boolean accepted = message.getPayload();

        logger.debug("\n\n제안 결과 메시지 도착\nMessage: {}\n", accepted);

        applicationEventPublisher.publishEvent(new ProposalResultArriveEvent(
                this,
                Clock.systemDefaultZone(),
                header.getSessionId(),
                accepted));
    }
}
