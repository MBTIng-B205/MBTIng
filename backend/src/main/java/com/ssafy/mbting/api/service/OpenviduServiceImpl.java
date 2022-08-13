package com.ssafy.mbting.api.service;

import io.openvidu.java.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newConcurrentMap;
import static com.google.common.collect.Sets.newConcurrentHashSet;

import static java.util.Optional.ofNullable;

@Service
public class OpenviduServiceImpl implements OpenviduService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OpenVidu openVidu;
    private final Map<String, Session> mapSessions;
    private final Map<String, Set<String>> mapSessionNamesTokens;

    public OpenviduServiceImpl(
            @Value("${com.mbting.openvidu.server.url}") String OPENVIDU_URL,
            @Value("${com.mbting.openvidu.server.secret}") String SECRET) {
        openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        mapSessions = newConcurrentMap();
        mapSessionNamesTokens = newConcurrentMap();
    }

    public String getToken(String sessionName) {
        logger.info("OpenVidu 토큰 생성 요청 들어옴");
        try {
            Session session = ofNullable(mapSessions.get(sessionName))
                    .orElse(openVidu.createSession());

            String token = session.createConnection(new ConnectionProperties.Builder()
                            .type(ConnectionType.WEBRTC)
                            .role(OpenViduRole.PUBLISHER)
                            .build())
                    .getToken();

            mapSessions.putIfAbsent(sessionName, session);
            Set<String> tokens = ofNullable(mapSessionNamesTokens.get(sessionName))
                    .orElse(newConcurrentHashSet());
            tokens.add(token);
            mapSessionNamesTokens.putIfAbsent(sessionName, tokens);

            logger.info("토큰을 성공적으로 발행함\nOpenVidu Token: {}", token);

            return token;
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            logger.error("토큰 생성 중 OpenVidu Error\n{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void removeUser(String sessionName, String token) {
        logger.info("OpenVidu 사용자 제거 요청 들어옴");
        ofNullable(mapSessionNamesTokens.get(sessionName)).ifPresent(tokens -> {
            tokens.remove(token);
            if (tokens.isEmpty()) {
                mapSessionNamesTokens.remove(sessionName);
                mapSessions.remove(sessionName);
                logger.info("세션이 비어서 없앰");
            }
        });
    }

}
