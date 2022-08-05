package com.ssafy.mbting.api.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class MeetingManager extends TextWebSocketHandler {
    //사람 세션 몇개인지
    //사람 정보 받아와서
    //몇명이 모이면 알고리즘 3사람 매칭하는지 정해야하는데
    //openvidu 알려줘야하는거
    //지역 index
    //관심사 index
    //성별 index
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private  static List<WebSocketSession> list = new ArrayList<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        TextMessage textMessage = new TextMessage("Welcome chatting server~ ^^");
        session.sendMessage(textMessage);
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);

        logger.info(session + " 클라이언트 접속");
        logger.info(list.size() + " list size");

        //알고리즘 적용

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        logger.info(session + " 클라이언트 접속 해제");
        list.remove(session);
    }


}
