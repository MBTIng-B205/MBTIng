package com.ssafy.mbting.ws.model.vo;

import lombok.*;

@Data
@Builder
public class StompUser {

    private String email;
    private MeetingUser meetingUser;

    public static StompUser of(String email) {
        return StompUser.builder()
                .email(email)
                .meetingUser(null)
                .build();
    }
}
