package com.ssafy.mbting.ws.eventListener;

import com.google.common.collect.Lists;
import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.ws.model.event.*;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.BaseMessageBody;
import com.ssafy.mbting.ws.model.vo.IndividualDestination;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.service.AppStompService;
import com.ssafy.mbting.ws.service.MeetingRoomService;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ssafy.mbting.api.request.AudioStageResult.GREEN;

@Component
@RequiredArgsConstructor
public class MeetingRoomAudioStageEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AppStompService appStompService;
    private final MeetingRoomService meetingRoomService;

    @Async
    @EventListener
    public void onMeetingRoomAudioStageResultArrive(MeetingRoomAudioStageResultArriveEvent event) {
        String sessionId = event.getSessionId();
        AudioStageResult voiceResult = event.getVoiceResult();

        logger.debug("\n\n음성 스테이지 결과 도착 이벤트 발생\nSession ID: {}\nGreen Light 여부: {}"
                , sessionId
                , voiceResult);

        meetingRoomService.setVoiceResultAndHandleIt(sessionId, voiceResult);
    }

    @Async
    @EventListener
    public void onMeetingRoomAudioStageResultsMade(MeetingRoomAudioStageResultsMadeEvent event) {
        String[] sessionIds = event.getSessionIds();
        AudioStageResult[] voiceResults = event.getVoiceResults();

        logger.debug("\n\n음성 스테이지 결과 모두 도착 이벤트 발생함\n1: ({}, {})\n2: ({}, {})\n"
                , sessionIds[0]
                , voiceResults[0]
                , sessionIds[1]
                , voiceResults[1]);

        if (voiceResults[0] == GREEN && voiceResults[1] == GREEN) {
            applicationEventPublisher.publishEvent(new MeetingRoomAudioStageBothGreenEvent(
                    this,
                    Clock.systemDefaultZone(),
                    sessionIds));
            return;
        }

        IntStream.range(0, 2).forEach(i -> {
            simpMessagingTemplate.convertAndSend(
                    IndividualDestination.of(appStompService
                            .getStompUserBySessionId(sessionIds[i])
                            .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                            .getEmail()).toString(),
                    BaseMessageBody.builder()
                            .command("noVideoStage")
                            .build());
        });
    }

    @Async
    @EventListener
    public void onMeetingRoomAudioStageBothGreen(MeetingRoomAudioStageBothGreenEvent event) {
        String[] sessionIds = event.getSessionIds();

        logger.debug("\n\n음성 스테이지 결과 모두 그린라이트 이벤트 발생함\nSession IDs: {}\n"
                , Arrays.toString(sessionIds));

        List<StompUser> stompUsers = IntStream.range(0, 2)
                .mapToObj(i -> appStompService
                        .getStompUserBySessionId(sessionIds[i])
                        .orElseThrow(() -> new RuntimeException("Session Not Found!")))
                .collect(Collectors.toCollection(Lists::newArrayList));

        logger.debug("\n\nemail1: {}\nemail2: {}\n"
                , stompUsers.get(0).getEmail()
                , stompUsers.get(1).getEmail());

        stompUsers.forEach(user -> simpMessagingTemplate.convertAndSend(
                IndividualDestination.of(user.getEmail()).toString(),
                BaseMessageBody.builder()
                        .command("goVideoStage")
                        .build()));
    }
}
