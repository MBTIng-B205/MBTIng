package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.repository.StompRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WaitingMeetingServiceImpl implements WaitingMeetingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final StompRepository stompRepository;
    private final MemberService memberService;
    private final OpenviduService openviduService;

    @Override
    public int getQueueSize() {
        return stompRepository.getQueueSize();
    }

    @Override
    public String getFirstSessionId() {
        logger.debug("\n\n대기열의 맨 앞 꺼내기 시도...\n");
        String sessionId = stompRepository.getFirstSessionId()
                .orElseThrow(() -> {
                    logger.error("\n\n!!! No Element !!!\n대기열이 비어 있음\n");
                    return new RuntimeException("Internal Server Error!");
                });
        stompRepository.leaveFromQueue(sessionId);
        logger.debug("\n\n잘 꺼냄\nsessionId: {}\n", sessionId);
        return sessionId;
    }

    @Override
    public void setMatchedMeetingUsers(String sessionId1, String sessionId2) {
        stompRepository.setMatchedMeetingUser(sessionId1, sessionId2);
        stompRepository.setMatchedMeetingUser(sessionId2, sessionId1);
    }

    @Override
    public String[] setMeetingRoomAndGetTokensForTwoUsers(String sessionId1, String sessionId2) {

        String meetingRoomId = UUID.randomUUID().toString();
        String openviduSessionName = UUID.randomUUID().toString();
        String[] tokens = {
                openviduService.getToken(openviduSessionName),
                openviduService.getToken(openviduSessionName)};


        stompRepository.saveMeetingRoom(meetingRoomId,
                MeetingRoom.newMeetingRoom(
                        openviduSessionName,
                        sessionId1,
                        sessionId2,
                        stompRepository.findStompUserBySessionId(sessionId1)
                                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                                .getMeetingUser().getMbti(),
                        stompRepository.findStompUserBySessionId(sessionId2)
                                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                                .getMeetingUser().getMbti()
                ));

        stompRepository.setMeetingRoomIdAndIndex(sessionId1, meetingRoomId, 0);
        stompRepository.setMeetingRoomIdAndIndex(sessionId2, meetingRoomId, 1);
        return tokens;
    }
}
