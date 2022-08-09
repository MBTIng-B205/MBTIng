package com.ssafy.mbting.ws.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.util.JwtTokenUtil;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.stompMessageHeader.SubscribeHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.repository.WaitingMeetingUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WaitingMeetingServiceImpl implements WaitingMeetingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final WaitingMeetingUserRepository waitingMeetingUserRepository;
    private final MemberService memberService;

    @Override
    public void connectUser(String sessionId, ConnectHeader connectHeader) {
        String accessToken = connectHeader.getAccessToken();
        String email = connectHeader.getEmail();

        if (!identicalTokenAndEmail(accessToken, email)) {
            logger.info("\n\naccessToken 과 email 이 매치되지 않음\n");
            throw new RuntimeException("Unauthorized!");
        }

        if (memberService.getUserByEmail(email) == null) {
            logger.info("\n\n해당 email 의 회원이 없음\n");
            throw new RuntimeException("No Member!");
        }

        if (waitingMeetingUserRepository.createSession(sessionId, StompUser.of(email)) != null) {
            logger.info("\n\nsessionId 가 이미 존재함\n");
            throw new RuntimeException("Already Exist!");
        }
    }

    @Override
    public void disconnectUser(String sessionId) {
        waitingMeetingUserRepository.removeSession(sessionId);
    }

    @Override
    public void takeUser(String sessionId, SubscribeHeader subscribeHeader, MessageChannel messageChannel) {
        if(waitingMeetingUserRepository.findBySessionId(sessionId).getMeetingUser() != null) {
            logger.info("\n\n이미 대기열에 들어감\n");
            throw new RuntimeException("Already Queued!");
        }
        MeetingUser meetingUser = MeetingUser.of(subscribeHeader);
        waitingMeetingUserRepository.queueMeetingUser(sessionId, meetingUser);
        waitingMeetingUserRepository.addSessionIdToFeatureUserTables(sessionId, meetingUser);

//        applicationEventPublisher.publishEvent(
//                new WaitingMeetingUserQueuedEvent(
//                        this,
//                        Clock.systemDefaultZone(),
//                        waitingMeetingUserRepository.findBySessionId(sessionId)));

//        throw new RuntimeException("에러");

        SimpMessagingTemplate simpMessagingTemplate = new SimpMessagingTemplate(messageChannel);

        simpMessagingTemplate.send("/ws/sub/indi/wp29dud@naver.com",
                MessageBuilder.createMessage("success",
                        new MessageHeaders(null)));

        logger.debug("\n\n테이크 유저 서비스 왔니?\n");
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
