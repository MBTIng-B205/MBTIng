package com.ssafy.mbting.ws.stompCommandHandler;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnsubscribeHandler implements StompCommandHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor, MessageChannel messageChannel) {
        // 구독 해제 처리 : Nothing to do
        logger.info("\n\n* {} *\nDestination: {}\n"
                , stompCommand
                , stompHeaderAccessor.getDestination());
    }
}
