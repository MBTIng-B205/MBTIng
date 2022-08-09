package com.ssafy.mbting.ws.stompCommandHandler;

import com.ssafy.mbting.ws.model.stompMessageHeader.StompConnectHeader;
import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConnectHandler implements StompCommandHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WaitingMeetingService waitingMeetingService;

    @Override
    public void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor) {
        // 연결 시작 처리 : 사용자 인증
        StompConnectHeader stompConnectHeader = StompConnectHeader.of(stompHeaderAccessor);

        logger.info("\n\n* {} *\nHeader: {}\n"
                , stompCommand
                , stompConnectHeader);

        if (!stompConnectHeader.isValid()) {
            logger.info("\n\n클라이언트가 Connect 시 헤더에 accessToken 또는 email 을 안 줌\n");
            throw new RuntimeException("No Authorization!");
        }

        waitingMeetingService.connectUser(stompHeaderAccessor.getSessionId(), stompConnectHeader);
    }
}
