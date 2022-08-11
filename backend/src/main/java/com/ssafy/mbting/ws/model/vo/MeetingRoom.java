package com.ssafy.mbting.ws.model.vo;

import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {

    private String openviduSessionName;
    @Builder.Default
    private Boolean[] meetingRoomStatus = new Boolean[2];
    @Builder.Default
    private String[] sessionIds = new String[2];
    @Builder.Default
    private AnalysisRegisterRequest[] meetingRoomResult = new AnalysisRegisterRequest[2];

    public static MeetingRoom newMeetingRoom(String openviduSessionName, String sessionId1, String sessionId2) {
        return MeetingRoom.builder()
                .openviduSessionName(openviduSessionName)
                .sessionIds(new String[]{sessionId1, sessionId2})
                .meetingRoomStatus(new Boolean[]{true, true})
                .build();
    }
}
