package com.ssafy.mbting.ws.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ssafy.mbting.common.util.JwtTokenUtil;
import com.ssafy.mbting.ws.model.event.OrphanOccurEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.model.stompMessageHeader.ConnectHeader;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;
import com.ssafy.mbting.ws.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppStompServiceImpl implements AppStompService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AppRepository appRepository;

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

        if (appRepository.createSession(
                sessionId,
                StompUser.ofBeforeSubscribe(email)) != null) {
            logger.info("\n\nsessionId 가 이미 존재함\n");
            throw new RuntimeException("Already Exist!");
        }
    }

    @Override
    public void disconnect(String sessionId) {
        Optional<StompUser> nullableStompUser = appRepository.findStompUserBySessionId(sessionId);
        if (!nullableStompUser.isPresent()) {
            logger.debug("\n\n세션이 이미 없어졌습니다. 아무 일도 하지 않습니다.\n");
            return;
        }
        StompUser stompUser = nullableStompUser.get();
        StompUserStatus status = stompUser.getStompUserStatus();
        logger.debug("\n\ndisconnect 시도...\n현재 상태: {}", status);
        switch (status) {
            case UNSUBSCRIBED:
                logger.debug("아무것도 안 합니다.");
                break;
            case INPROGRESS:
                logger.debug("매치된 유저가 있는지 확인합니다.");
                handleOrphanUserIfPresent(stompUser);
                break;
            case INQUEUE:
                logger.debug("큐에서 빼줍니다.");
                appRepository.leaveFromQueue(sessionId);
                break;
            case INROOM:
                // Todo: 룸에서 빼는 거 구현해야 함
                logger.debug("미팅룸을 파합니다.");
                handleOrphanUserIfPresent(stompUser);
                break;
            default:
                logger.debug("알 수 없는 상태입니다. 매치된 유저가 있는지 확인합니다.");
                handleOrphanUserIfPresent(stompUser);
                break;
        }
        appRepository.removeSession(sessionId);
    }

    @Override
    public void subscribe(String sessionId, MeetingUser meetingUser) {
        appRepository.saveMeetingUser(sessionId, meetingUser);
        appRepository.joinToQueue(sessionId);
        applicationEventPublisher.publishEvent(new WaitingMeetingUserQueuedEvent(
                this,
                Clock.systemDefaultZone()
        ));
    }

    @Override
    public Optional<StompUser> getStompUserBySessionId(String sessionId) {
        return appRepository.findStompUserBySessionId(sessionId);
    }

    private void handleOrphanUserIfPresent(StompUser stompUser) {
        String sessionId = stompUser.getMatchedMeetingUserSessionId();
        appRepository.findStompUserBySessionId(sessionId).ifPresent(user -> {
            logger.debug("\n\n고아가 있습니다.\n");
            applicationEventPublisher.publishEvent(new OrphanOccurEvent(
                    this,
                    Clock.systemDefaultZone(),
                    sessionId));
        });
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
