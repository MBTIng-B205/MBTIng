package com.ssafy.mbting.ws.stompCommandHandler;

import com.ssafy.mbting.ws.service.WaitingMeetingService;
import lombok.RequiredArgsConstructor;
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
    private final WaitingMeetingService waitingMeetingService;

    @Override
    public void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor, MessageChannel messageChannel) {
        if (stompHeaderAccessor.getHeartbeat().length != 0) {
            // 클라이언트로부터의 연결 해제 메시지 옴 : Nothing to do
            logger.info("\n\n* {} *\n", stompCommand);
            return;
        }
        // 연결 해제 처리 : 사용자 제거
        logger.info("\n\n* {} *\n", stompCommand);

        waitingMeetingService.disconnectUser(stompHeaderAccessor.getSessionId());
    }
}
