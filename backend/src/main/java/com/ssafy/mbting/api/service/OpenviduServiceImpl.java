package com.ssafy.mbting.api.service;

import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OpenviduServiceImpl implements OpenviduService {

    private OpenVidu openVidu; // openvidu 선언

    private Map<String, Session> mapSessions = new ConcurrentHashMap<>(); // mapSessions m
    private Map<String, String> mapSessionNamesTokens = new ConcurrentHashMap<>();

    private String OPENVIDU_URL="https://i7b205.p.ssafy.io:4443/";
    private String SECRET="MY_SECRET";

    public String getToken(String sessionName) {
        openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        OpenViduRole role = OpenViduRole.PUBLISHER;
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).role(role).build();
        if (this.mapSessions.get(sessionName) != null) {
            String token = this.mapSessionNamesTokens.get(sessionName);
            return token;
        }
        try {
            Session session = this.openVidu.createSession();
            String token = session.createConnection(connectionProperties).getToken();
            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, token);
            return token;
        } catch (Exception e) {
            return String.valueOf(e);
        }
    }

    public void removeUser(String sessionName) {
        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {
                mapSessionNamesTokens.remove(sessionName);
                this.mapSessions.remove(sessionName);
                return;
        }
    }

}
