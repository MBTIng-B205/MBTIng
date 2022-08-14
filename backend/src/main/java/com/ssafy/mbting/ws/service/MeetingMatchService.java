package com.ssafy.mbting.ws.service;

public interface MeetingMatchService {

    int getEnoughSizeToStartMatching();
    void startMatching();
}
