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
public class SendHandler implements StompCommandHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AppStompService appStompService;

    @Override
    public void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor, MessageChannel messageChannel) {
        // 메시지 처리 : 사용자 구독 확인
        String destination = stompHeaderAccessor.getDestination();

        logger.info("\n\n* {} *\n\nDestination: {}\n", stompCommand, destination);

        if (destination == null || !destination.startsWith("/ws/msg/indi/")) {
            logger.info("\n\n클라이언트가 \"/ws/msg/indi/*\" 가 아닌 곳으로 메시지를 보내려 합니다.\n시도한 Destination: {}\n"
                    , destination);
            throw new RuntimeException("Not Allowed!");
        }

        if (appStompService
                .getStompUserBySessionId(stompHeaderAccessor.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session Not Found!"))
                .getMeetingUser() == null) {
            throw new RuntimeException("Bad Request!");
        }
    }
}
