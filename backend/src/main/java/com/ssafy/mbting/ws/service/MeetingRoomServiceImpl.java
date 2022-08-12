package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.api.request.AudioStageResult;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.ws.model.event.MeetingRoomAudioStageResultsMadeEvent;
import com.ssafy.mbting.ws.model.event.ProposalResultsMadeEvent;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.repository.StompRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final StompRepository stompRepository;
    private final MemberService memberService;
    private final OpenviduService openviduService;

    @Override
    public void setProposalAcceptedAndHandleIt(String sessionId, Boolean accepted) {
        stompRepository.setProposalAccepted(sessionId, accepted);

        String matchedSessionId = stompRepository.findStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .getMatchedMeetingUserSessionId();

        logger.debug("\n\nsubject: {}\nmatched: {}\n", sessionId, matchedSessionId);

        Boolean opponentAccepted = stompRepository.findStompUserBySessionId(matchedSessionId)
                .orElseThrow(() -> {
                    logger.debug("\n\n이미 상대가 떠났습니다.\n");
                    applicationEventPublisher.publishEvent(new ProposalResultsMadeEvent(
                            this,
                            Clock.systemDefaultZone(),
                            sessionId,
                            accepted,
                            matchedSessionId,
                            false));
                    return new RuntimeException("Session Not Found!");
                }).getProposalAccepted();

        logger.debug("\n\n상대 수락 여부: {}\n", opponentAccepted);

        ofNullable(opponentAccepted).ifPresent(oppoA -> applicationEventPublisher
                .publishEvent(new ProposalResultsMadeEvent(
                        this,
                        Clock.systemDefaultZone(),
                        sessionId,
                        accepted,
                        matchedSessionId,
                        oppoA)));
    }

    @Override
    public void setVoiceResultAndHandleIt(String sessionId, AudioStageResult subjectVoiceResult) {
        stompRepository.setVoiceResult(sessionId, subjectVoiceResult);

        StompUser stompUser = stompRepository.findStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String meetingRoomId = ofNullable(stompUser.getMeetingRoomId())
                .orElseThrow(() -> new RuntimeException("Not In Room!"));
        int indexOnRoom = ofNullable(stompUser.getIndexOnRoom())
                .orElseThrow(() -> new RuntimeException("Not In Room!"));
        int opponentIndexOnRoom = indexOnRoom == 0 ? 1 : 0;
        MeetingRoom meetingRoom = stompRepository.findMeetingRoomByMeetingRoomId(meetingRoomId)
                .orElseThrow(() -> new RuntimeException("Meeting Room Not Found!"));
        AudioStageResult opponentVoiceResult = meetingRoom.getMeetingRoomResult()[opponentIndexOnRoom].getVoiceResult();

        logger.debug("\n\n상대의 음성 스테이지 결정: {}\n", opponentVoiceResult);

        ofNullable(opponentVoiceResult).ifPresent(oppoV -> applicationEventPublisher
                .publishEvent(new MeetingRoomAudioStageResultsMadeEvent(
                        this,
                        Clock.systemDefaultZone(),
                        sessionId,
                        subjectVoiceResult,
                        meetingRoom.getSessionIds()[opponentIndexOnRoom],
                        oppoV)));
    }
}
