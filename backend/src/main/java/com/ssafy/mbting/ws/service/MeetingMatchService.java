package com.ssafy.mbting.ws.service;

public interface MeetingMatchService {

    void setInProgress(boolean inProgress);
    int getEnoughSizeToStartMatching();
    void startMatching();
}
