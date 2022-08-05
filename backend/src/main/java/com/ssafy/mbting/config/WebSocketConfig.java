package com.ssafy.mbting.config;

import com.ssafy.mbting.api.controller.MachingManager;
import com.ssafy.mbting.api.controller.MeetingManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MeetingManager webSocketHandler;
    private final MachingManager webSocketHandler2;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/*").setAllowedOrigins("*").addHandler(webSocketHandler2,"/ws/*/*").setAllowedOrigins();
    }
}