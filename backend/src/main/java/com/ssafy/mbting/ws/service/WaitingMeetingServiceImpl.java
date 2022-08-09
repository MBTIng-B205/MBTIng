package com.ssafy.mbting.ws.service;

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

    public void connectUser(StompConnectHeader stompConnectHeader) {
//        try {
//            JWTVerifier verifier = JwtTokenUtil.getVerifier();
//            DecodedJWT decodedJWT = verifier.verify(accessToken);
//            String userEmail = decodedJWT.getSubject();
//
//            if (email == null || !email.equals(userEmail)) throw new RuntimeException("Unauthorized!");
//        } catch (JWTVerificationException e) {
//            throw new RuntimeException("Unauthorized!");
//        }
//
//        waitingMeetingUserQueue.createSession(sessionId, email);
    }

    public void disconnectUser(String sessionId) {
        waitingMeetingUserQueue.removeSession(sessionId);
    }

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
}
