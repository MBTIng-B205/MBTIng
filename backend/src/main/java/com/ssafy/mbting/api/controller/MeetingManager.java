package com.ssafy.mbting.api.controller;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.auth.MemberDetails;
import com.ssafy.mbting.common.util.JwtTokenUtil;
import com.ssafy.mbting.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Component
public class MeetingManager extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
    private List<String> memberpool =  new ArrayList<>();

    //1 . a id -> session a  -> a id session map <id,WebsocketSession>
    //2 . map< String , Set<String> >   관심사         <야구 , id> // <축구 ,id>
    //3 . map< String , Set<String >>   지역
    //4 . map <String, Set<String> >    성별
    //5   queue를 구현해서 id 꺼내서
    //6   map을 순회하면서 id  후보자를 생각해서
    //7   매칭queue<id> 안에 넣는다

    private final MemberService memberService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        TextMessage textMessage = new TextMessage("Welcome chatting server~ ^^");
        session.sendMessage(textMessage);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info(session.getHandshakeHeaders().get("access-token").toString());
        String token=session.getHandshakeHeaders().get("access-token").toString();
        JWTVerifier verifier = JwtTokenUtil.getVerifier();
        token = token.replace("[","");
        token = token.replace("]","");
        DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
        String userId = decodedJWT.getSubject();
        Member member=memberService.getUserByEmail(userId);

        memberpool.add(member.getEmail());
        sessionList.add(session);

        logger.info(member.getNickname() + "님이 입장하셨습니다.");
        logger.info("session 풀 사이즈:" +sessionList.size());
        logger.info("member 풀 사이즈:" +memberpool.size());

        if(memberpool.size() >=3){
            logger.info("알고리즘 실행 시켜 줘야한다 ");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("#ChattingHandler, afterConnectionClosed");
        /*
        String token=session.getHandshakeHeaders().get("access-token").toString();
        JWTVerifier verifier = JwtTokenUtil.getVerifier();
        token = token.replace("[","");
        token = token.replace("]","");
        DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
        String userId = decodedJWT.getSubject();
        Member member=memberService.getUserByEmail(userId);
        */
        sessionList.remove(session);
    //        memberpool.remove(member.getEmail());
    //       logger.info(member.getNickname()+ "님이 퇴장하셨습니다.");
        logger.info("session 풀 사이즈:" +sessionList.size());
        logger.info("member 풀 사이즈:" +memberpool.size());
    }
}
