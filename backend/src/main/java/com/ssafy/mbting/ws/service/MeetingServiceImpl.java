package com.ssafy.mbting.ws.service;

import com.google.common.collect.Maps;
import com.ssafy.mbting.ws.event.MeetingUserMatchedEvent;
import com.ssafy.mbting.ws.event.WaitingMeetingUserEnoughEvent;
import com.ssafy.mbting.ws.model.MeetingUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private static final int ENOUGH_WAITING_SIZE = 3;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationEventPublisher publisher;
    private final SortedSet<String> meetingUserEmailSet = new ConcurrentSkipListSet<>();
    private final Map<String, MeetingUser> meetingUserEmailMeetingUserMap = Maps.newConcurrentMap();
    private final Map<MeetingUser.Gender, SortedSet<String>> genderMeetingUserEmailSetMap = Maps.newConcurrentMap();
    private final Map<String, SortedSet<String>> interestMeetingUserEmailSetMap = Maps.newConcurrentMap();

    @PostConstruct
    private void init() {
        genderMeetingUserEmailSetMap.put(MeetingUser.Gender.MALE, new ConcurrentSkipListSet<>());
        genderMeetingUserEmailSetMap.put(MeetingUser.Gender.FEMALE, new ConcurrentSkipListSet<>());
    }

    public void addUser(MeetingUser meetingUser) {
        meetingUserEmailSet.add(meetingUser.getEmail());
        meetingUserEmailMeetingUserMap.put(meetingUser.getEmail(), meetingUser);
        genderMeetingUserEmailSetMap.get(meetingUser.getGender()).add(meetingUser.getEmail());
        meetingUser.getInterests().forEach((interest) -> {
            interestMeetingUserEmailSetMap.computeIfAbsent(interest, key -> {
                SortedSet<String> newSet = new ConcurrentSkipListSet<>();
                newSet.add(meetingUser.getEmail());
                return newSet;
            });
            interestMeetingUserEmailSetMap.computeIfPresent(interest, (key, oldValue) -> {
                oldValue.add(meetingUser.getEmail());
                return oldValue;
            });
        });

        logger.info("\n\nsize: {}\n", meetingUserEmailSet.size());

        if (meetingUserEmailSet.size() >= ENOUGH_WAITING_SIZE) {
            publisher.publishEvent(new WaitingMeetingUserEnoughEvent(this, Clock.systemDefaultZone()));
        }
    }

    @Async
    @EventListener
    public void onEnough(WaitingMeetingUserEnoughEvent event) {
        logger.info("\n\n이거 왔나?????\n");
        if (event.isIntervalEnough()) {
            String meetingUser1Email = meetingUserEmailSet.first();
            meetingUserEmailSet.remove(meetingUser1Email);
            String meetingUser2Email = meetingUserEmailSet.first();
            meetingUserEmailSet.remove(meetingUser2Email);
            logger.info("\n\nuser1: {}\nuser2: {}\n", meetingUser1Email, meetingUser2Email);
            publisher.publishEvent(new MeetingUserMatchedEvent(this,
                    Clock.systemDefaultZone(),
                    meetingUserEmailMeetingUserMap.get(meetingUser1Email),
                    meetingUserEmailMeetingUserMap.get(meetingUser2Email)));
        }
    }
}
