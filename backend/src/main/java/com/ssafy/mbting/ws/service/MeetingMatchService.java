package com.ssafy.mbting.ws.service;

public interface MeetingMatchService {

    boolean getInProgress();
    void setInProgress(boolean inProgress);
    int getEnoughSizeToStartMatching();
    void startMatching();
}
