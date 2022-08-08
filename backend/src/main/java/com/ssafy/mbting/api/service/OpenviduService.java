package com.ssafy.mbting.api.service;

public interface OpenviduService {
    public String getToken(String sessionName);

    public void removeUser(String sessionName);
}