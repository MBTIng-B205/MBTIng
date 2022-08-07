package com.ssafy.mbting.ws.service;

import com.ssafy.mbting.ws.event.WaitingMeetingUserEnoughEvent;
import com.ssafy.mbting.ws.model.MeetingUser;

public interface MeetingService {
    void addUser(MeetingUser meetingUser);
    void onEnough(WaitingMeetingUserEnoughEvent event);
}
