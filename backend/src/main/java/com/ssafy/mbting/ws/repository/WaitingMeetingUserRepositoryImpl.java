package com.ssafy.mbting.ws.repository;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class WaitingMeetingUserRepositoryImpl implements WaitingMeetingUserRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, StompUser> sessionIdStompUserMap = Maps.newConcurrentMap();
    private final LinkedHashSet<String> waitingMeetingUserQueue = new LinkedHashSet<>();
    private final Map<String, MeetingRoom> meetingRoomIdMeetingRoomMap = Maps.newConcurrentMap();
    private final Map<Gender, Set<String>> genderSessionIdMap = Maps.newConcurrentMap();
    private final Map<String, Set<String>> sidoSessionIdMap = Maps.newConcurrentMap();
    private final Map<String, Set<String>> interestSessionIdMap = Maps.newConcurrentMap();

    @PostConstruct
    private void init() {
        genderSessionIdMap.put(Gender.MALE, Sets.newHashSet());
        genderSessionIdMap.put(Gender.FEMALE, Sets.newHashSet());
    }

    @Override
    public StompUser createSession(String sessionId, StompUser stompUser) {
        StompUser oldValue = sessionIdStompUserMap.put(sessionId, stompUser);
        logger.debug("\n\nsessionIdStompUserMap에 ({}, {}) 넣음\n"
                , sessionId
                , stompUser);
        return oldValue;
    }

    @Override
    public void removeSession(String sessionId) {
        sessionIdStompUserMap.remove(sessionId);
        logger.debug("\n\nsessionIdStompUserMap에서 세션 \"{}\" 제거함\n", sessionId);
    }

    @Override
    public void saveMeetingUser(String sessionId, MeetingUser meetingUser) {
        StompUser stompUser = sessionIdStompUserMap.get(sessionId);
        if (stompUser == null) {
            logger.error("\n\n세션이 존재하지 않음\nSession ID: {}\n", sessionId);
            throw new RuntimeException("No Session!");
        }
        stompUser.setMeetingUser(meetingUser);
        stompUser.setStompUserStatus(StompUserStatus.INPROGRESS);
        logger.debug("\n\n세션 \"{}\" 에 미팅 유저 정보 {} 추가 후\n상태 변경함 INPROGRESS\n", sessionId, meetingUser);
    }

    @Override
    public void joinToQueue(String sessionId) {
        StompUser stompUser = sessionIdStompUserMap.get(sessionId);
        stompUser.cleanForJoiningToQueue();
        waitingMeetingUserQueue.add(sessionId);
        addSessionIdToFeatureUserTables(sessionId);
        stompUser.setStompUserStatus(StompUserStatus.INQUEUE);
        logger.debug("\n\n세션 \"{}\" 의 제안 정보 초기화 후\nQueue, FetureTables 에 추가 후\n상태 변경함 INQUEUE\n", sessionId);
    }

    @Override
    public void leaveFromQueue(String sessionId) {
        waitingMeetingUserQueue.remove(sessionId);
        removeSessionIdFromFeatureUserTables(sessionId);
        sessionIdStompUserMap.get(sessionId).setStompUserStatus(StompUserStatus.INPROGRESS);
        logger.debug("\n\nQueue, FeatureTables 에서 \"{}\" 제거 후 상태 변경함 INPROGRESS\n", sessionId);
    }

    @Override
    public void setMatchedMeetingUser(String subjectSessionId, String matchedSessionId) {
        sessionIdStompUserMap.get(subjectSessionId).setMatchedMeetingUserSessionId(matchedSessionId);
        logger.debug("\n\n세션 \"{}\" 에 \"{}\" 제안됨 세팅\n", subjectSessionId, matchedSessionId);
    }

    @Override
    public void setProposalAccepted(String sessionId, Boolean accepted) {
        sessionIdStompUserMap.get(sessionId).setProposalAccepted(accepted);
        logger.debug("\n\n세션 \"{}\" 에 제안 수락 여부를 {} 로 세팅\n", sessionId, accepted);
    }

    @Override
    public void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom) {
        meetingRoomIdMeetingRoomMap.put(meetingRoomId, meetingRoom);
        logger.debug("\n\nmeetingRoomIdMeetingRoomMap에\n({}, {}) 추가함\n", meetingRoomId, meetingRoom);
    }

    @Override
    public void setMeetingRoomIdAndIndex(String sessionId, String meetingRoomId, Integer indexOnRoom) {
        StompUser stompUser = sessionIdStompUserMap.get(sessionId);
        stompUser.setMeetingRoomId(meetingRoomId);
        stompUser.setIndexOnRoom(indexOnRoom);
        stompUser.setStompUserStatus(StompUserStatus.INROOM);
        logger.debug("\n\n세션 \"{}\" 에 미팅 룸 \"{}[{}]\" 세팅 후 상태 변경 INROOM\n"
                , sessionId
                , meetingRoomId
                , indexOnRoom);
    }

    @Override
    public Optional<StompUser> findBySessionId(String sessionId) {
        Optional<StompUser> stompUser = Optional.ofNullable(sessionIdStompUserMap.get(sessionId));
        stompUser.ifPresent(user -> logger.debug("\n\nStompUser found\n({}, {})\n", sessionId, user));
        return stompUser;
    }

    @Override
    public int getQueueSize() {
        int size = waitingMeetingUserQueue.size();
        logger.debug("\n\n현재 대기열 크기: {}\n", size);
        return size;
    }

    @Override
    public Optional<String> getFirstSessionId() {
        Optional<String> firstSessionId = waitingMeetingUserQueue.stream().findFirst();
        logger.debug("\n\n대기열의 맨 앞 Session ID: {}\n", firstSessionId);
        return firstSessionId;
    }

    private void addSessionIdToFeatureUserTables(String sessionId) {
        MeetingUser meetingUser = sessionIdStompUserMap.get(sessionId).getMeetingUser();
        genderSessionIdMap.get(meetingUser.getGender()).add(sessionId);
        String sido = meetingUser.getSido();
        sidoSessionIdMap.putIfAbsent(sido, Sets.newConcurrentHashSet());
        sidoSessionIdMap.get(sido).add(sessionId);
        meetingUser.getInterests().forEach(interest -> {
            interestSessionIdMap.putIfAbsent(interest, Sets.newConcurrentHashSet());
            interestSessionIdMap.get(interest).add(sessionId);
        });
        logger.debug("\n\nFeatureUserTables 에 \"{}\" 넣음\nMeetingUser: {}\n", sessionId, meetingUser);
    }

    private void removeSessionIdFromFeatureUserTables(String sessionId) {
        MeetingUser meetingUser = sessionIdStompUserMap.get(sessionId).getMeetingUser();
        genderSessionIdMap.get(meetingUser.getGender()).remove(sessionId);
        sidoSessionIdMap.get(meetingUser.getSido()).remove(sessionId);
        meetingUser.getInterests().forEach(
                interest -> interestSessionIdMap.get(interest).remove(sessionId));
        logger.debug("\n\nFeatureUserTables 에서 \"{}\" 뺌\nMeetingUser: {}\n", sessionId, meetingUser);
    }
}
