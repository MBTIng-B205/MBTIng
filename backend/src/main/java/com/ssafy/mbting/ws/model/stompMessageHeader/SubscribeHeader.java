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
public class SubscribeHeader {

    private String destination;
    private String mbti;
    private String gender;
    private String sido;
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public boolean isValid() {
        return getMbti() != null
                && getGender() != null
                && getSido() != null;
    }

    public static SubscribeHeader of(StompHeaderAccessor stompHeaderAccessor) {
        return SubscribeHeader.builder()
                .destination(stompHeaderAccessor.getDestination())
                .mbti(stompHeaderAccessor.getFirstNativeHeader("mbti"))
                .gender(stompHeaderAccessor.getFirstNativeHeader("gender"))
                .sido(stompHeaderAccessor.getFirstNativeHeader("sido"))
                .interests(stompHeaderAccessor.getNativeHeader("interests"))
                .build();
    }
}
