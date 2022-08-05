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

import java.util.*;

@RequiredArgsConstructor
@Component
public class MeetingManager extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
    private Map<String,WebSocketSession> sessionMap= new HashMap<String,WebSocketSession>();
    private Map<String, Set<String> > interestMap = new HashMap<>();
    private Map<String, Set<String> > locationMap = new HashMap<>();
    private Map<String, Set<String> > genderMap = new HashMap<>();

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
        TextMessage textMessage = new TextMessage("meeting wait..");
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
        session.getAttributes().put("email",member.getEmail());

        sessionMap.put(member.getEmail(),session);
        updateSido(member.getSido(),member.getEmail());
        updateGender(member.isGender(),member.getEmail());

        logger.info(session.getAttributes().get("email") + "님이 입장하셨습니다.");
        logger.info("session 풀 사이즈:" +sessionMap.size());
        logger.info("locationMap 풀 사이즈:" +locationMap.entrySet().toString());
        logger.info("genderMap 풀 사이즈:" +genderMap.entrySet().toString());

        if(sessionMap.size() >=1){
            TextMessage textMessage = new TextMessage("meeting wait..");
            //string json

            session.sendMessage(textMessage);
        }
    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = (String) session.getAttributes().get("email");
        Member member=memberService.getUserByEmail(userId);

        sessionMap.remove(member.getEmail());

        deleteSido(member.getSido(),member.getEmail());
        deleteGender(member.isGender(),member.getEmail());

        logger.info(session.getAttributes().get("email")+ "님이 퇴장하셨습니다.");
        logger.info("session 풀 사이즈:" +sessionMap.size());
        logger.info("locationMap 풀 사이즈:" +locationMap.entrySet().toString());
        logger.info("genderMap 풀 사이즈:" +genderMap.entrySet().toString());
    }


    private void updateSido(String sido,String email) {
        if (sido==null) {
            if (!locationMap.containsKey(sido)) {
                Set<String> sidoset = new HashSet<>();
                sidoset.add(sido);
                locationMap.put(sido, sidoset);
            } else {
                locationMap.get(sido).add(email);
            }
        }
    }

    private void updateGender(boolean gender, String email) {
        if(gender) {
            if (!genderMap.containsKey("male")) {
                Set<String> genderset = new HashSet<>();
                genderset.add(email);
                genderMap.put("male", genderset);
            } else {
                genderMap.get("male").add(email);
            }
        }
        else{
            if (!genderMap.containsKey("femail")) {
                Set<String> genderset = new HashSet<>();
                genderset.add(email);
                genderMap.put("femail", genderset);
            } else {
                genderMap.get("femail").add(email);
            }
        }
    }

    private void deleteSido(String sido,String email){
        locationMap.get(sido).remove(email);
    }

    private void deleteGender(boolean gender, String email){
        if (gender){
            genderMap.get("male").remove(email);
        }
        else{
            genderMap.get("femail").remove(email);
        }
    }
}
