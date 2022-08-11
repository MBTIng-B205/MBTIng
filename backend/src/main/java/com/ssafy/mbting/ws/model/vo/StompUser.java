package com.ssafy.mbting.ws.model.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StompUser {

    private String email;
    private StompUserStatus stompUserStatus;
    private MeetingUser meetingUser;
    private String matchedMeetingUserSessionId;
    private Boolean proposalAccepted;
    private String meetingRoomId;
    private Integer indexOnRoom;

    public void cleanForJoiningToQueue() {
        setMatchedMeetingUserSessionId(null);
        setProposalAccepted(null);
        setMeetingRoomId(null);
        setIndexOnRoom(null);
    }

    public static StompUser ofBeforeSubscribe(String email) {
        return StompUser.builder()
                .email(email)
                .stompUserStatus(StompUserStatus.UNSUBSCRIBED)
                .build();
    }
}
