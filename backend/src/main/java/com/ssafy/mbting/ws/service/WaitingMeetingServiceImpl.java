package com.ssafy.mbting.ws.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.common.util.JwtTokenUtil;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;
import com.ssafy.mbting.ws.repository.WaitingMeetingUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.time.Clock;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WaitingMeetingServiceImpl implements WaitingMeetingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final WaitingMeetingUserRepository waitingMeetingUserRepository;
    private final MemberService memberService;
    private final OpenviduService openviduService;

    @Override
    public void connect(String sessionId, ConnectHeader connectHeader) {
        String accessToken = connectHeader.getAccessToken();
        String email = connectHeader.getEmail();

//        if (!identicalTokenAndEmail(accessToken, email)) {
//            logger.info("\n\naccessToken 과 email 이 매치되지 않음\n");
//            throw new RuntimeException("Unauthorized!");
//        }
//
//        if (memberService.getUserByEmail(email) == null) {
//            logger.info("\n\n해당 email 의 회원이 없음\n");
//            throw new RuntimeException("No Member!");
//        }

        if (waitingMeetingUserRepository.createSession(
                sessionId,
                StompUser.ofBeforeSubscribe(email)) != null) {
            logger.info("\n\nsessionId 가 이미 존재함\n");
            throw new RuntimeException("Already Exist!");
        }
    }

    @Override
    public void disconnect(String sessionId) {
        StompUserStatus status;
        try {
            status = waitingMeetingUserRepository.getStompUserStatus(sessionId);
        } catch (NullPointerException e) {
            logger.debug("\n\n세션이 이미 없어졌습니다. 아무 일도 하지 않습니다.\n");
            return;
        }
        logger.debug("\n\ndisconnect 시도...");
        switch (status) {
            case UNSUBSCRIBED:
                // 여기서는 할 게 없음
                break;
            case INPROGRESS:
                // 여기서도 할 게 없음
                break;
            case INQUEUE:
                waitingMeetingUserRepository.leaveFromQueue(sessionId);
                break;
            case INROOM:
                // Todo: 룸에서 빼는 거 구현해야 함
                break;
            default:
                // 미지의 세계, 해결할 수 없는 상태
                break;
        }
        waitingMeetingUserRepository.removeSession(sessionId);
    }

    @Override
    public void subscribe(String sessionId, MeetingUser meetingUser) {
        waitingMeetingUserRepository.saveMeetingUser(sessionId, meetingUser);
        waitingMeetingUserRepository.joinToQueue(sessionId);
        applicationEventPublisher.publishEvent(new WaitingMeetingUserQueuedEvent(
                this,
                Clock.systemDefaultZone()
        ));
    }

    @Override
    public void setMatchedMeetingUsers(String sessionId1, String sessionId2) {
        waitingMeetingUserRepository.setMatchedMeetingUser(sessionId1, sessionId2);
        waitingMeetingUserRepository.setMatchedMeetingUser(sessionId2, sessionId1);
    }

    @Override
    public String[] setMeetingRoomAndGetTokensForTwoUsers(String sessionId1, String sessionId2) {

        String meetingRoomId = UUID.randomUUID().toString();
        String openviduSessionName = UUID.randomUUID().toString();
        String[] tokens = {
                openviduService.getToken(openviduSessionName),
                openviduService.getToken(openviduSessionName)};

        waitingMeetingUserRepository.saveMeetingRoom(meetingRoomId,
                MeetingRoom.newMeetingRoom(openviduSessionName, sessionId1, sessionId2));

        waitingMeetingUserRepository.setMeetingRoomIdAndIndex(sessionId1, meetingRoomId, 0);
        waitingMeetingUserRepository.setMeetingRoomIdAndIndex(sessionId2, meetingRoomId, 1);
        return tokens;
    }

    @Override
    public void rejoin(String sessionId) {
        waitingMeetingUserRepository.joinToQueue(sessionId);
    }

    @Override
    public int getQueueSize() {
        return waitingMeetingUserRepository.getQueueSize();
    }

    @Override
    public String getFirstSessionId() {
        logger.debug("\n\n대기열의 맨 앞 꺼내기 시도...\n");
        String sessionId = waitingMeetingUserRepository.getFirstSessionId()
                .orElseThrow(() -> {
                    logger.error("\n\n!!! No Element !!!\n대기열이 비어 있음\n");
                    return new RuntimeException("Internal Server Error!");
                });
        waitingMeetingUserRepository.leaveFromQueue(sessionId);
        logger.debug("\n\n잘 꺼냄\nsessionId: {}\n", sessionId);
        return sessionId;
    }

    @Override
    public StompUser getStompUserBySessionId(String sessionId) {
        return waitingMeetingUserRepository.findBySessionId(sessionId);
    }

    @Override
    public void setProposalAccepted(String sessionId, Boolean accepted) {
        // Todo: 구현
    }

    private boolean identicalTokenAndEmail(String accessToken, String email) {
        try {
            JWTVerifier verifier = JwtTokenUtil.getVerifier();
            DecodedJWT decodedJWT = verifier.verify(accessToken);
            String emailDecoded = decodedJWT.getSubject();
            return email != null && email.equals(emailDecoded);
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
