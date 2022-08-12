package com.ssafy.mbting.ws.repository;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ssafy.mbting.api.request.AudioStageResult;
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

import static java.util.Optional.*;

@Repository
public class AppRepositoryImpl implements AppRepository {

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
        StompUser stompUser = ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
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
        StompUser stompUser = ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
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
        ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .setStompUserStatus(StompUserStatus.INPROGRESS);
        logger.debug("\n\nQueue, FeatureTables 에서 \"{}\" 제거 후 상태 변경함 INPROGRESS\n", sessionId);
    }

    @Override
    public void setMatchedMeetingUser(String subjectSessionId, String matchedSessionId) {
        ofNullable(sessionIdStompUserMap.get(subjectSessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .setMatchedMeetingUserSessionId(matchedSessionId);
        logger.debug("\n\n세션 \"{}\" 에 \"{}\" 제안됨 세팅\n", subjectSessionId, matchedSessionId);
    }

    @Override
    public void setProposalAccepted(String sessionId, Boolean accepted) {
        ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .setProposalAccepted(accepted);
        logger.debug("\n\n세션 \"{}\" 에 제안 수락 여부를 {} 로 세팅\n", sessionId, accepted);
    }

    @Override
    public Optional<MeetingRoom> findMeetingRoomByMeetingRoomId(String meetingRoomId) {
        Optional<MeetingRoom> meetingRoom = ofNullable(meetingRoomIdMeetingRoomMap.get(meetingRoomId));
        meetingRoom.ifPresent(room -> logger.debug("\n\nMeetingRoom found\n({}, {})\n", meetingRoomId, room));
        return meetingRoom;
    }

    @Override
    public void setVoiceResult(String sessionId, AudioStageResult voiceResult) {
        StompUser stompUser = ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        String meetingRoomId = stompUser.getMeetingRoomId();
        Integer indexOnRoom = stompUser.getIndexOnRoom();
        ofNullable(meetingRoomIdMeetingRoomMap.get(meetingRoomId))
                .orElseThrow(() -> new RuntimeException("Room Not Found!"))
                .getMeetingRoomResult()[indexOnRoom]
                .setVoiceResult(voiceResult);

        logger.debug("\n\n세션 \"{}\" 의 음성 스테이지 결과를 {} 로 세팅\nMeeting Room ID: {}\nIndex On Room: {}\n"
                , sessionId
                , voiceResult
                , meetingRoomId
                , indexOnRoom);
    }

    @Override
    public void saveMeetingRoom(String meetingRoomId, MeetingRoom meetingRoom) {
        meetingRoomIdMeetingRoomMap.put(meetingRoomId, meetingRoom);
        logger.debug("\n\nmeetingRoomIdMeetingRoomMap에\n({}, {}) 추가함\n", meetingRoomId, meetingRoom);
    }

    @Override
    public void setMeetingRoomIdAndIndex(String sessionId, String meetingRoomId, Integer indexOnRoom) {
        StompUser stompUser = ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"));
        stompUser.setMeetingRoomId(meetingRoomId);
        stompUser.setIndexOnRoom(indexOnRoom);
        stompUser.setStompUserStatus(StompUserStatus.INROOM);
        logger.debug("\n\n세션 \"{}\" 에 미팅 룸 \"{}[{}]\" 세팅 후 상태 변경 INROOM\n"
                , sessionId
                , meetingRoomId
                , indexOnRoom);
    }

    @Override
    public Optional<StompUser> findStompUserBySessionId(String sessionId) {
        Optional<StompUser> stompUser = ofNullable(sessionIdStompUserMap.get(sessionId));
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
        MeetingUser meetingUser = ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .getMeetingUser();
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
        MeetingUser meetingUser = ofNullable(sessionIdStompUserMap.get(sessionId))
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .getMeetingUser();
        genderSessionIdMap.get(meetingUser.getGender()).remove(sessionId);
        sidoSessionIdMap.get(meetingUser.getSido()).remove(sessionId);
        meetingUser.getInterests().forEach(
                interest -> interestSessionIdMap.get(interest).remove(sessionId));
        logger.debug("\n\nFeatureUserTables 에서 \"{}\" 뺌\nMeetingUser: {}\n", sessionId, meetingUser);
    }
}
