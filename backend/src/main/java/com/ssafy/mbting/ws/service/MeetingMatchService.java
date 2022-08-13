package com.ssafy.mbting.ws.service;

public interface MeetingMatchService {

    boolean isEnoughSizeToStartMatching(int size);
    void startMatching();
}
