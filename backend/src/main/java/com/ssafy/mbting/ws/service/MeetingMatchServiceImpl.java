package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.algorithm.MatchingScoreBoard;
import com.ssafy.mbting.ws.model.event.waiting.RequestToStartMatchingEvent;
import com.ssafy.mbting.ws.model.event.waiting.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private boolean inProgress = false;
    private int sizeToRetryMatching = 2;
    @Value("${com.mbting.match.condition.enough.size}")
    private int enoughSizeToStartMatching;
    @Value("${com.mbting.match.condition.score.gender}")
    private int genderScore;
    @Value("${com.mbting.match.condition.score.sido}")
    private int sidoScore;
    @Value("${com.mbting.match.condition.score.interest}")
    private int interestScore;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AppRepository appRepository;

    public int getEnoughSizeToStartMatching() {
        return enoughSizeToStartMatching;
    }

    public boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
        logger.debug("inProgress 를 강제로 {} 로 바꿨습니다.", inProgress);
    }

    public void startMatching() {
        try {

            logger.debug("\n\n\n          == ****************************** ==\n          == ******* START MATCHING ******* ==\n          == ****************************** ==\n\n");

            if (inProgress) {
                logger.debug("매칭이 진행중이기 때문에 매칭을 시작하지 않습니다.");
                return;
            }
            inProgress = true;

            String subject, matched;

            subject = appRepository.getFirstSessionId().orElseThrow(() -> new RuntimeException("Queue Empty!"));

            appRepository.leaveFromQueue(subject);

            MeetingUser subjectFeatures = ofNullable(appRepository.findStompUserBySessionId(subject)
                    .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                    .getMeetingUser()).orElseThrow(() -> new RuntimeException("No Meeting User!"));

            MatchingScoreBoard scoreBoard = MatchingScoreBoard.builder()
                    .genderScore(genderScore)
                    .sidoScore(sidoScore)
                    .interestScore(interestScore)
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

            appRepository.leaveFromQueue(matched);

            applicationEventPublisher.publishEvent(new WaitingMeetingUserMatchedEvent(
                    this,
                    Clock.systemDefaultZone(),
                    subject,
                    matched));

            inProgress = false;

            applicationEventPublisher.publishEvent(new RequestToStartMatchingEvent(
                    this,
                    Clock.systemDefaultZone(),
                    sizeToRetryMatching));
            
        } catch (Exception e) {
            logger.error("매칭 중 오류 발생: {}", e.getMessage());
            inProgress = false;

            applicationEventPublisher.publishEvent(new RequestToStartMatchingEvent(
                    this,
                    Clock.systemDefaultZone(),
                    sizeToRetryMatching));
        }
    }
}
