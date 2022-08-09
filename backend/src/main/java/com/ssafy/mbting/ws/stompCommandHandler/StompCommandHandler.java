package com.ssafy.mbting.ws.stompCommandHandler;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface StompCommandHandler {
    void handle(StompCommand stompCommand, StompHeaderAccessor stompHeaderAccessor);
}
