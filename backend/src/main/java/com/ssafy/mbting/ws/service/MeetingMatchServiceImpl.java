package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.algorithm.MatchingScoreBoard;
import com.ssafy.mbting.ws.model.event.waiting.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.ssafy.mbting.db.enums.Gender.*;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MeetingMatchServiceImpl implements MeetingMatchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AppRepository appRepository;

    public void startMatching() {
        // Todo: 구현

        String subject, matched;

        subject = appRepository.getFirstSessionId().orElseThrow(() -> new RuntimeException("Queue Empty!"));

        MeetingUser subjectFeatures = ofNullable(appRepository.findStompUserBySessionId(subject)
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .getMeetingUser()).orElseThrow(() -> new RuntimeException("No Meeting User!"));

        MatchingScoreBoard scoreBoard = MatchingScoreBoard.builder()
                .genderScore(100)
                .sidoScore(10)
                .interestScore(1)
                .build();

        Gender targetGender = subjectFeatures.getGender() == MALE ? FEMALE : MALE;
        logger.debug("targetGender: {}", targetGender);
        appRepository.getGenderTable().forEach((gender, ids) -> {
            if (gender == targetGender) ids.forEach(scoreBoard::addGenderScore);
        });

        String subjectSido = subjectFeatures.getSido();
        logger.debug("subjectSido: {}", subjectSido);
        appRepository.getSidoTable().forEach((sido, ids) -> {
            if (Objects.equals(sido, subjectSido)) ids.forEach(scoreBoard::addSidoScore);
        });

        Map<String, Set<String>> interestTable = appRepository.getInterestTable();
        subjectFeatures.getInterests().forEach(subjectInterest -> {
            logger.debug("subjectInterest: {}", subjectInterest);
            interestTable.computeIfPresent(subjectInterest, (interest, ids) -> {
                ids.forEach(scoreBoard::addInterestScore);
                return ids;
            });
        });

        matched = scoreBoard.getBestTarget();

        applicationEventPublisher.publishEvent(new WaitingMeetingUserMatchedEvent(
                this,
                Clock.systemDefaultZone(),
                subject,
                matched));
    }
}
