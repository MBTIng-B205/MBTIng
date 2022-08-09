package com.ssafy.mbting.ws.model.stompMessageHeader;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
public class StompSubscribeHeader {

    private String sessionId;
    private String destination;
    private String gender;
    private String sido;
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public static StompSubscribeHeader of(StompHeaderAccessor stompHeaderAccessor) {
        return StompSubscribeHeader.builder()
                .sessionId(stompHeaderAccessor.getSessionId())
                .destination(stompHeaderAccessor.getDestination())
                .gender(stompHeaderAccessor.getFirstNativeHeader("gender"))
                .sido(stompHeaderAccessor.getFirstNativeHeader("region"))
                .interests(stompHeaderAccessor.getNativeHeader("interests"))
                .build();
    }
}
