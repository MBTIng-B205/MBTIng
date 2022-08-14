package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.ws.model.event.room.MeetingRoomAudioStageResultsMadeEvent;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AppRepository appRepository;
    private final OpenviduService openviduService;

    @Override
    public String[] setMeetingRoomAndGetTokensForTwoUsers(String sessionId1, String sessionId2) {

        String meetingRoomId = UUID.randomUUID().toString();
        String openviduSessionName = UUID.randomUUID().toString();
        String[] tokens = {
                openviduService.getToken(openviduSessionName),
                openviduService.getToken(openviduSessionName)};

        logger.debug("\n\n미팅룸 생성 (시작 시각 기록)\n");

        appRepository.saveMeetingRoom(meetingRoomId,
                MeetingRoom.newMeetingRoom(
                        openviduSessionName,
                        tokens,
                        sessionId1,
                        sessionId2,
                        appRepository.findStompUserBySessionId(sessionId1)
                                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                                .getMeetingUser().getMbti(),
                        appRepository.findStompUserBySessionId(sessionId2)
                                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                                .getMeetingUser().getMbti()
                ));

        appRepository.setMeetingRoomIdAndIndex(sessionId1, meetingRoomId, 0);
        appRepository.setMeetingRoomIdAndIndex(sessionId2, meetingRoomId, 1);
        return tokens;
    }

    @Override
    public void leaveFromMeetingRoomAndRemoveIfEmpty(String sessionId) {
        appRepository.findStompUserBySessionId(sessionId).ifPresent(user -> {
            String meetingRoomId = user.getMeetingRoomId();
            appRepository.findMeetingRoomByMeetingRoomId(meetingRoomId).ifPresent(room -> {
                appRepository.setMeetingRoomStatusToFalse(meetingRoomId, user.getIndexOnRoom());
                Boolean[] status = room.getMeetingRoomStatus();
                if (!status[0] && !status[1]) {
                    // Todo: 여기서 통계 발행해야 함

                    logger.debug("\n\n통계를 위해 미팅룸 조회\nMeeting Room ID: {}\nMeeting Room: {}\n"
                            , meetingRoomId
                            , room);

                    String openviduSessionName = room.getOpenviduSessionName();
                    String[] openviduTokens = room.getOpenviduTokens();

                    openviduService.removeUser(openviduSessionName, openviduTokens[0]);
                    openviduService.removeUser(openviduSessionName, openviduTokens[1]);

                    logger.debug("\n\n미팅룸에 사용자 없음\n관련 OpenVidu 정보 제거함\nSession Name: {}\nTokens: {}\n"
                            , openviduSessionName
                            , openviduTokens);

                    appRepository.removeMeetingRoom(meetingRoomId);
                } else {
                    logger.debug("\n\n미팅룸에 사용자 있음 \"{}\"\nMeeting Room: {}\n"
                            , meetingRoomId
                            , room);
                }
            });
        });
    }

    @Override
    public void setVoiceResultAndHandleIt(String sessionId, AudioStageResult subjectVoiceResult) {
        appRepository.setVoiceResult(sessionId, subjectVoiceResult);

        StompUser stompUser = appRepository.findStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String meetingRoomId = ofNullable(stompUser.getMeetingRoomId())
                .orElseThrow(() -> new RuntimeException("Not In Room!"));
        int indexOnRoom = ofNullable(stompUser.getIndexOnRoom())
                .orElseThrow(() -> new RuntimeException("Not In Room!"));
        int opponentIndexOnRoom = indexOnRoom == 0 ? 1 : 0;
        MeetingRoom meetingRoom = appRepository.findMeetingRoomByMeetingRoomId(meetingRoomId)
                .orElseThrow(() -> new RuntimeException("Meeting Room Not Found!"));
        AudioStageResult opponentVoiceResult = meetingRoom.getMeetingRoomResults()[opponentIndexOnRoom].getVoiceResult();

        logger.debug("\n\n상대의 음성 스테이지 결정: {}\n", opponentVoiceResult);

        ofNullable(opponentVoiceResult).ifPresent(oppoV -> applicationEventPublisher
                .publishEvent(new MeetingRoomAudioStageResultsMadeEvent(
                        this,
                        Clock.systemDefaultZone(),
                        meetingRoomId,
                        sessionId,
                        subjectVoiceResult,
                        meetingRoom.getSessionIds()[opponentIndexOnRoom],
                        oppoV)));
    }

    @Override
    public void setVideoStageStartTime(String meetingRoomId) {
        appRepository.findMeetingRoomByMeetingRoomId(meetingRoomId).ifPresent(room -> {
            LocalDateTime now = LocalDateTime.now();
            logger.debug("\n\n화상 시작 시각 세팅\n미팅룸 ID: {}\n현재 시각: {}\n"
                    , meetingRoomId
                    , now);
            AnalysisRegisterRequest[] results = room.getMeetingRoomResults();
            results[0].setMiddleTime(now);
            results[1].setMiddleTime(now);
        });
    }

    @Override
    public void setEndTimeIfAbsent(String meetingRoomId) {
        appRepository.findMeetingRoomByMeetingRoomId(meetingRoomId).ifPresent(room -> {
            logger.debug("\n\n종료 시각 세팅\n여러 번 불릴 수 있으므로 이미 값이 있다면 Skip\n");
            LocalDateTime now = LocalDateTime.now();
            Arrays.stream(room.getMeetingRoomResults())
                    .filter(result -> !ofNullable(result.getEndTime()).isPresent())
                    .forEach(result -> result.setEndTime(now));
        });
    }

    @Override
    public void setFriendResult(String sessionId, Boolean addOrRemove) {
        appRepository.findStompUserBySessionId(sessionId).ifPresent(user -> {
            int indexOnRoom = user.getIndexOnRoom();
            String meetingRoomId = user.getMeetingRoomId();
            appRepository.findMeetingRoomByMeetingRoomId(meetingRoomId).ifPresent((room -> {
                room.getMeetingRoomResults()[indexOnRoom].setFriendResult(addOrRemove);
                logger.debug("\n\n미팅룸 {}[{}] 의 친구 추가 여부를 True 로 세팅\nMeeting Room: {}\n"
                        , meetingRoomId
                        , indexOnRoom
                        , room);
            }));
        });
    }
}
