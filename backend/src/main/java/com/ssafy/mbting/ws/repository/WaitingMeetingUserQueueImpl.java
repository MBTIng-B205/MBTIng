package com.ssafy.mbting.ws.repository;

import com.google.common.collect.Maps;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

@Repository
public class WaitingMeetingUserQueueImpl implements WaitingMeetingUserQueue{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int ENOUGH_WAITING_MEETING_USER_QUEUE_SIZE = 3;
    private final Map<String, String> sessionIdEmailMap = Maps.newConcurrentMap();
    private final SortedSet<MeetingUser> meetingUsers = new ConcurrentSkipListSet<>();
    private final Map<String, MeetingUser> emailMeetingUserMap = Maps.newConcurrentMap();

    @PostConstruct
    private void init() {
    }

    @Override
    public void createSession(String sessionId, String email) {
        if (sessionIdEmailMap.put(sessionId, email) != null) {
            logger.info("\n\nsessionId 가 이미 존재함\n");
            throw new RuntimeException("SessionId Already Exists!");
        }
    }

    @Override
    public void removeSession(String sessionId) {

    }

    @Override
    public void takeUser(MeetingUser meetingUser) {

    }

    @Override
    public boolean hasSubscribedDestinationBySessionId(String sessionId) {
        return false;
    }

    @Override
    public MeetingUser findByEmail(String email) {
        return null;
    }

    @Override
    public String peekMeetingUserEmail() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean hasEnoughSize() {
        return false;
    }
}
