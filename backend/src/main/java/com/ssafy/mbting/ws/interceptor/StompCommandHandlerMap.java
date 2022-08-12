package com.ssafy.mbting.ws.interceptor;

import com.ssafy.mbting.ws.service.WaitingMeetingService;
import com.ssafy.mbting.ws.stompCommandHandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static org.springframework.messaging.simp.stomp.StompCommand.*;

@Service
public class StompCommandHandlerMap extends HashMap<StompCommand, StompCommandHandler> {

    @Autowired
    public StompCommandHandlerMap(WaitingMeetingService waitingMeetingService
            , ConnectHandler connectHandler
            , DisconnectHandler disconnectHandler
            , SubscribeHandler subscribeHandler
            , UnsubscribeHandler unsubscribeHandler
            , SendHandler sendHandler
    ) {
        put(CONNECT, connectHandler);
        put(DISCONNECT, disconnectHandler);
        put(SUBSCRIBE, subscribeHandler);
        put(UNSUBSCRIBE, unsubscribeHandler);
        put(SEND, sendHandler);
    }
}
