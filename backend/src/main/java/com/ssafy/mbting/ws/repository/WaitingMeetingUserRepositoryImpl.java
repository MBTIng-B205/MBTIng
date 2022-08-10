package com.ssafy.mbting.ws.repository;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.vo.MeetingRoom;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
        return sessionIdStompUserMap.put(sessionId, stompUser);
    }

    @Override
    public void removeSession(String sessionId) {
        sessionIdStompUserMap.remove(sessionId);
    }

    @Override
    public StompUserStatus getStompUserStatus(String sessionId) {
        return null;
    }

    @Override
    public void saveMeetingUser(String sessionId, MeetingUser meetingUser) {
        StompUser stompUser = sessionIdStompUserMap.get(sessionId);
        stompUser.setMeetingUser(meetingUser);
        stompUser.setStompUserStatus(StompUserStatus.INPROGRESS);
    }

    @Override
    public void joinToQueue(String sessionId) {
        waitingMeetingUserQueue.add(sessionId);
        sessionIdStompUserMap.get(sessionId).setStompUserStatus(StompUserStatus.INQUEUE);
    }

    @Override
    public void leaveFromQueue(String sessionId) {
        waitingMeetingUserQueue.remove(sessionId);
        sessionIdStompUserMap.get(sessionId).setStompUserStatus(StompUserStatus.INPROGRESS);
    }

    @Override
    public void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom) {
        meetingRoomIdMeetingRoomMap.put(meetingRoomId, meetingRoom);
    }

    @Override
    public StompUser findBySessionId(String sessionId) {
        return sessionIdStompUserMap.get(sessionId);
    }

    @Override
    public void addSessionIdToFeatureUserTables(String sessionId, MeetingUser meetingUser) {
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
        genderSessionIdMap.get(meetingUser.getGender()).remove(sessionId);
        sidoSessionIdMap.get(meetingUser.getSido()).remove(sessionId);
        meetingUser.getInterests().forEach(
                interest -> interestSessionIdMap.get(interest).remove(sessionId));
    }
}
