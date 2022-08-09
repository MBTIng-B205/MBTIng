package com.ssafy.mbting.ws.stompCommandHandler;

import com.ssafy.mbting.ws.model.stompMessageHeader.SubscribeHeader;
import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscribeHandler implements StompCommandHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WaitingMeetingService waitingMeetingService;

    @Override
    public void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor) {
        // 구독 시작 처리 : 소개팅 대기 유저로 등록
        SubscribeHeader subscribeHeader;
        try {
            subscribeHeader = SubscribeHeader.of(stompHeaderAccessor);
        } catch (NullPointerException e) {
            logger.error("\n\n!!! {} !!!\n클라이언트가 Subscribe 시 헤더에 gender 또는 region 을 안 준 경우\n"
                    , e.getMessage());
            throw new RuntimeException("Bad Request!");
        }
        logger.info("\n\n* {} *\nHeader: {}\n"
                , stompCommand
                , subscribeHeader);

        try {
            waitingMeetingService.takeUser(stompHeaderAccessor.getSessionId(), subscribeHeader);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Server Error!");
        }
    }
}
