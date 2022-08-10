package com.ssafy.mbting.ws.model.vo;

import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import lombok.*;

@Getter
@Setter
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
}
