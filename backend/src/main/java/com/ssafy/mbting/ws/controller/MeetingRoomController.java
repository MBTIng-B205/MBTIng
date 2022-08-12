package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.ws.model.event.MeetingRoomAudioStageResultArriveEvent;
import com.ssafy.mbting.ws.model.stompMessageBody.msg.AudioStageResultBody;
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
public class MeetingRoomController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;

    @MessageMapping("/indi/meetingAudioStageResult")
    public void receiveAudioStarted(@Payload Message<AudioStageResultBody> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);

        AudioStageResult voiceResult = message.getPayload().getResult();

        logger.debug("\n\n음성 스테이지 결과 도착\nMessage: {}\n", voiceResult);

        applicationEventPublisher.publishEvent(new MeetingRoomAudioStageResultArriveEvent(
                this,
                Clock.systemDefaultZone(),
                header.getSessionId(),
                voiceResult));
    }
}
