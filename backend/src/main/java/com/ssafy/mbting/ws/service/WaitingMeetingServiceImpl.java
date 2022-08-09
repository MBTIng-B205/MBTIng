package com.ssafy.mbting.ws.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.util.JwtTokenUtil;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import com.ssafy.mbting.ws.model.stompMessageHeader.StompConnectHeader;
import com.ssafy.mbting.ws.model.stompMessageHeader.StompSubscribeHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.repository.WaitingMeetingUserQueue;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class WaitingMeetingServiceImpl implements WaitingMeetingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher publisher;
    private final WaitingMeetingUserQueue waitingMeetingUserQueue;
    private final MemberService memberService;

    @Override
    public void connectUser(StompConnectHeader stompConnectHeader) {
        String sessionId = stompConnectHeader.getSessionId();
        String accessToken = stompConnectHeader.getAccessToken();
        String email = stompConnectHeader.getEmail();

        if (!identicalTokenAndEmail(accessToken, email)) {
            logger.info("\n\naccessToken 과 email 이 매치되지 않음\n");
            throw new RuntimeException("Unauthorized!");
        }

        if (memberService.getUserByEmail(email) == null) {
            logger.info("\n\n해당 email 의 회원이 없음\n");
            throw new RuntimeException("No Member!");
        }

        waitingMeetingUserQueue.createSession(sessionId, email);
    }

    @Override
    public void disconnectUser(String sessionId) {
        waitingMeetingUserQueue.removeSession(sessionId);
    }

    @Override
    public void takeUser(StompSubscribeHeader subscribeHeader) {
        MeetingUser meetingUser = null;
        waitingMeetingUserQueue.takeUser(meetingUser);

        logger.info("\n\nsize: {}\n", waitingMeetingUserQueue.size());

        if (waitingMeetingUserQueue.hasEnoughSize()) {
            publisher.publishEvent(new WaitingMeetingUserQueueSizeEnoughEvent(this, Clock.systemDefaultZone()));
        }
    }

    @Override
    public boolean hasSubscribedDestinationBySessionId(String sessionId) {
        return false;
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
