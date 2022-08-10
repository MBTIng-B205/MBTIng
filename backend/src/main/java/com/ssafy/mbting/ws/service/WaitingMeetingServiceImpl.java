package com.ssafy.mbting.ws.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.common.util.JwtTokenUtil;
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
        waitingMeetingUserRepository.removeSession(sessionId);
    }

    @Override
    public void subscribe(String sessionId, MeetingUser meetingUser) {
        waitingMeetingUserRepository.saveMeetingUser(sessionId, meetingUser);
        waitingMeetingUserRepository.joinToQueue(sessionId);
    }

    @Override
    public MeetingUser saveAndGetMatchedMeetingUser(String subjectSessionId, String matchedSessionId) {
        // Todo ...
        waitingMeetingUserRepository.findBySessionId(subjectSessionId)
                .setMatchedMeetingUserSessionId(matchedSessionId);
        return waitingMeetingUserRepository.findBySessionId(matchedSessionId)
                .getMeetingUser();
    }

    @Override
    public String[] getTokensForTwoUsers(String sessionId1, String sessionId2) {
        waitingMeetingUserRepository.leaveFromQueue(sessionId1);
        waitingMeetingUserRepository.leaveFromQueue(sessionId2);

        String openviduSessionName = UUID.randomUUID().toString();
        String token1 = openviduService.getToken(openviduSessionName);
        String token2 = openviduService.getToken(openviduSessionName);
        String[] tokens = {token1, token2};

        String meetingRoomId = UUID.randomUUID().toString();
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .openviduSessionName(openviduSessionName)
                .sessionIds(new String[]{sessionId1, sessionId2})
                .meetingRoomStatus(new Boolean[]{true, true})
                .build();

        waitingMeetingUserRepository.saveMeetingRoom(meetingRoomId, meetingRoom);

        waitingMeetingUserRepository.findBySessionId(sessionId1).setIndexOnRoom(0);
        waitingMeetingUserRepository.findBySessionId(sessionId2).setIndexOnRoom(1);
        waitingMeetingUserRepository.findBySessionId(sessionId1).setStompUserStatus(StompUserStatus.INROOM);
        waitingMeetingUserRepository.findBySessionId(sessionId2).setStompUserStatus(StompUserStatus.INROOM);
        return tokens;
    }

    @Override
    public void rejoin(String sessionId) {
        waitingMeetingUserRepository.joinToQueue(sessionId);
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
