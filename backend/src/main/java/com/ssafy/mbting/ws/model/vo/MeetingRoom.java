package com.ssafy.mbting.ws.model.vo;

import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {

    private String openviduSessionName;
    private String[] sessionIds;
    private Boolean[] meetingRoomStatus;
    private AnalysisRegisterRequest[] meetingRoomResult;

    public static MeetingRoom newMeetingRoom(
            String openviduSessionName,
            String sessionId1,
            String sessionId2,
            String mbti1,
            String mbti2) {
        LocalDateTime now = LocalDateTime.now();
        return MeetingRoom.builder()
                .openviduSessionName(openviduSessionName)
                .sessionIds(new String[]{sessionId1, sessionId2})
                .meetingRoomStatus(new Boolean[]{true, true})
                .meetingRoomResult(new AnalysisRegisterRequest[]{
                        AnalysisRegisterRequest.builder()
                                .fromMbti(mbti1).toMbti(mbti2)
                                .startTime(now).build(),
                        AnalysisRegisterRequest.builder()
                                .fromMbti(mbti2).toMbti(mbti1)
                                .startTime(now).build()})
                .build();
    }
}
