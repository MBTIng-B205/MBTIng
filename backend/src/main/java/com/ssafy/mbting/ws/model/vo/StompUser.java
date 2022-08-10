package com.ssafy.mbting.ws.model.vo;

import lombok.*;

@Data
@Builder
public class StompUser {

    private String email;
    private StompUserStatus stompUserStatus;
    private MeetingUser meetingUser;
    private String matchedMeetingUserSessionId;
    private Boolean proposalAccepted;
    private String meetingRoomId;
    private Integer indexOnRoom;

    public static StompUser ofBeforeSubscribe(String email) {
        return StompUser.builder()
                .email(email)
                .stompUserStatus(StompUserStatus.UNSUBSCRIBED)
                .build();
    }
}
