package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.api.request.ReportRegisterRequest;
import com.ssafy.mbting.api.service.ReportService;
import com.ssafy.mbting.ws.model.event.room.AddFriendEvent;
import com.ssafy.mbting.ws.model.event.room.MeetingRoomAudioStageResultArriveEvent;
import com.ssafy.mbting.ws.model.stompMessageBody.msg.AudioStageResultBody;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.Clock;

@Controller
@RequiredArgsConstructor
public class MeetingRoomController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ReportService reportService;
    @MessageMapping("/indi/meetingAudioStageResult")
    public void receiveAudioStarted(Message<AudioStageResultBody> message) {
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);

        AudioStageResult voiceResult = message.getPayload().getResult();

        logger.debug("\n\n음성 스테이지 결과 도착\nMessage: {}\n", voiceResult);

        applicationEventPublisher.publishEvent(new MeetingRoomAudioStageResultArriveEvent(
                this,
                Clock.systemDefaultZone(),
                header.getSessionId(),
                voiceResult));
    }

    // Todo: 친추 처리
    @MessageMapping("/indi/addFriend")
    public void receiveFriendRequest(Message<Void> message) {

        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);

        logger.debug("\n\n친구 추가 클릭 도착\nMessage: {}\n", message);

        // Todo: 이벤트 발행
        applicationEventPublisher.publishEvent(new AddFriendEvent(
                this,
                Clock.systemDefaultZone(),
                header.getSessionId()));
    }

    @MessageMapping("/indi/createReport")
    public void receiveReportRequest(Message<ReportRegisterRequest> message){
        logger.debug("\n\n신고 하기 요청 도착\nMessage: {}\n", message);
        StompHeaderAccessor header = StompHeaderAccessor.wrap(message);
        ReportRegisterRequest reportRegisterRequest = message.getPayload();
        logger.debug("\n\n신고 하기 요청 정보\n reportRegisterRequest: {}\n", reportRegisterRequest);
        reportService.createReport(reportRegisterRequest);
    }
}
