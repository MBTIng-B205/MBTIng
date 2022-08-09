package com.ssafy.mbting.ws.model.stompMessageHeader;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Data
@Builder
@ToString
public class StompConnectHeader {

    private String sessionId;
    private String accessToken;
    private String email;

    public boolean isValid() {
        return getEmail() != null && getAccessToken() != null;
    }

    public static StompConnectHeader of(StompHeaderAccessor stompHeaderAccessor) {
        return StompConnectHeader.builder()
                .sessionId(stompHeaderAccessor.getSessionId())
                .accessToken(stompHeaderAccessor.getFirstNativeHeader("accessToken"))
                .email(stompHeaderAccessor.getFirstNativeHeader("email"))
                .build();
    }
}
