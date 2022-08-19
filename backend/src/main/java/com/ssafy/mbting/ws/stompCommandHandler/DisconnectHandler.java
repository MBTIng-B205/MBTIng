package com.ssafy.mbting.ws.stompCommandHandler;

import com.ssafy.mbting.ws.service.AppStompService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisconnectHandler implements StompCommandHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AppStompService appStompService;

    @Override
    public void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor, MessageChannel messageChannel) {
        // 연결 해제 처리 : 사용자 제거
        String sessionId = stompHeaderAccessor.getSessionId();

        logger.info("\n\n* {} *\nSession ID: {}\n"
                , stompCommand
                , sessionId);

        appStompService.disconnect(sessionId);
    }
}
