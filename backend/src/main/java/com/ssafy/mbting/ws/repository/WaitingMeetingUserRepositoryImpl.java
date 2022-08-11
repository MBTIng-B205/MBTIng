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
    private final Map<String, StompUser> sessionIdStompUserMap = Maps.newHashMap();
    private final LinkedHashSet<String> waitingMeetingUserQueue = new LinkedHashSet<>();
    private final Map<String, MeetingRoom> meetingRoomIdMeetingRoomMap = Maps.newHashMap();
    private final Map<Gender, Set<String>> genderSessionIdMap = Maps.newEnumMap(Gender.class);
    private final Map<String, Set<String>> sidoSessionIdMap = Maps.newHashMap();
    private final Map<String, Set<String>> interestSessionIdMap = Maps.newHashMap();

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
    public StompUserStatus getStompUserStatus(String sessionId) {
        StompUserStatus stompUserStatus = sessionIdStompUserMap.get(sessionId).getStompUserStatus();
        logger.debug("\n\n세션 \"{}\" 현재 상태: {}\n", sessionId, stompUserStatus);
        return stompUserStatus;
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
        logger.debug("\n\n세션 \"{}\" 추가 후 상태 변경함 INPROGRESS\n", sessionId);
    }

    @Override
    public void joinToQueue(String sessionId) {
        if (waitingMeetingUserQueue.add(sessionId)) {
            logger.debug("\n\n대기열에 세션을 추가함\nSession ID: {}\n", sessionId);
        } else {
            logger.debug("\n\n대기열에 이미 \"{}\"세션이 존재함\n", sessionId);
        }
        sessionIdStompUserMap.get(sessionId).setStompUserStatus(StompUserStatus.INQUEUE);
        logger.debug("\n\n상태 변경함 INQUEUE\n");
    }

    @Override
    public void leaveFromQueue(String sessionId) {
        waitingMeetingUserQueue.remove(sessionId);
        sessionIdStompUserMap.get(sessionId).setStompUserStatus(StompUserStatus.INPROGRESS);
        logger.debug("\n\n대기열에서 \"{}\" 제거 후 상태 변경함 INPROGRESS\n", sessionId);
    }

    @Override
    public void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom) {
        meetingRoomIdMeetingRoomMap.put(meetingRoomId, meetingRoom);
        logger.debug("\n\nmeetingRoomIdMeetingRoomMap에\n({}, {}) 추가함\n", meetingRoomId, meetingRoom);
    }

    @Override
    public StompUser findBySessionId(String sessionId) {
        // Todo: 로그 찍기
        return sessionIdStompUserMap.get(sessionId);
    }

    @Override
    public void addSessionIdToFeatureUserTables(String sessionId, MeetingUser meetingUser) {
        // Todo: 로그 찍기
        genderSessionIdMap.get(meetingUser.getGender()).add(sessionId);
        String sido = meetingUser.getSido();
        sidoSessionIdMap.putIfAbsent(sido, Sets.newHashSet());
        sidoSessionIdMap.get(sido).add(sessionId);
        meetingUser.getInterests().forEach(interest -> {
            interestSessionIdMap.putIfAbsent(interest, Sets.newHashSet());
            interestSessionIdMap.get(interest).add(sessionId);
        });
    }

    @Override
    public void removeSessionIdFromFeatureUserTables(String sessionId, MeetingUser meetingUser) {
        // Todo: 로그 찍기
        genderSessionIdMap.get(meetingUser.getGender()).remove(sessionId);
        sidoSessionIdMap.get(meetingUser.getSido()).remove(sessionId);
        meetingUser.getInterests().forEach(
                interest -> interestSessionIdMap.get(interest).remove(sessionId));
    }

    @Override
    public int getQueueSize() {
        // Todo: 로그 찍기
        return sessionIdStompUserMap.size();
    }

    @Override
    public Optional<String> getFirstSessionId() {
        // Todo: 로그 찍기
        return waitingMeetingUserQueue.stream().findFirst();
    }
}
