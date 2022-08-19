package com.ssafy.mbting.ws.model.stompMessageHeader;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Data
@Builder
@ToString
public class ConnectHeader {

    private String accessToken;
    private String email;

    public boolean isValid() {
        return getEmail() != null && getAccessToken() != null;
    }

    public static ConnectHeader of(StompHeaderAccessor stompHeaderAccessor) {
        return ConnectHeader.builder()
                .accessToken(stompHeaderAccessor.getFirstNativeHeader("accessToken"))
                .email(stompHeaderAccessor.getFirstNativeHeader("email"))
                .build();
    }
}
