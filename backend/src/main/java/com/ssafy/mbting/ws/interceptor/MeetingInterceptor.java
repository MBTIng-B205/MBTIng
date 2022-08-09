package com.ssafy.mbting.ws.interceptor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingInterceptor implements ChannelInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final StompCommandHandlerMap stompCommandHandlerMap;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        logger.debug("\n\n============================\n==== 메시지 인터셉터 시작 ====\n");

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);

        String sessionId = stompHeaderAccessor.getSessionId();
        MessageHeaders messageHeaders = stompHeaderAccessor.getMessageHeaders();
        StompCommand stompCommand = stompHeaderAccessor.getCommand();

        logger.info("\n\nSession ID: {}\nCommand: {}\nMessage Headers: {}\n"
                , sessionId
                , stompCommand
                , messageHeaders);

        stompCommandHandlerMap
                .getOrDefault(stompCommand,
                        (command, header) -> logger.info("\n\n* {} *\n", command))
                .handle(stompCommand, stompHeaderAccessor);

        logger.debug("\n\n==== 메시지 인터셉터 끝 ====\n==========================\n");

        return message;
    }
}
