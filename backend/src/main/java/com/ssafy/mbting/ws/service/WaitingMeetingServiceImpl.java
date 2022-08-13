package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.model.event.ProposalResultsMadeEvent;
import com.ssafy.mbting.ws.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class WaitingMeetingServiceImpl implements WaitingMeetingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AppRepository appRepository;

    @Override
    public int getQueueSize() {
        return appRepository.getQueueSize();
    }

    @Override
    public String getFirstSessionId() {
        logger.debug("\n\n대기열의 맨 앞 꺼내기 시도...\n");
        String sessionId = appRepository.getFirstSessionId()
                .orElseThrow(() -> {
                    logger.error("\n\n!!! No Element !!!\n대기열이 비어 있음\n");
                    return new RuntimeException("Internal Server Error!");
                });
        appRepository.leaveFromQueue(sessionId);
        logger.debug("\n\n잘 꺼냄\nsessionId: {}\n", sessionId);
        return sessionId;
    }

    @Override
    public void setMatchedMeetingUsers(String sessionId1, String sessionId2) {
        appRepository.setMatchedMeetingUser(sessionId1, sessionId2);
        appRepository.setMatchedMeetingUser(sessionId2, sessionId1);
    }

    @Override
    public void setProposalAcceptedAndHandleIt(String sessionId, Boolean accepted) {
        appRepository.setProposalAccepted(sessionId, accepted);

        String matchedSessionId = appRepository.findStompUserBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .getMatchedMeetingUserSessionId();

        logger.debug("\n\nsubject: {}\nmatched: {}\n", sessionId, matchedSessionId);

        Boolean opponentAccepted = appRepository.findStompUserBySessionId(matchedSessionId)
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
}
