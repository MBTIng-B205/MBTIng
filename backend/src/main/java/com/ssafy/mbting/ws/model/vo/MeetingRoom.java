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
    private String[] openviduTokens;
    private String[] sessionIds;
    private Boolean[] meetingRoomStatus;
    private AnalysisRegisterRequest[] meetingRoomResults;

    public static MeetingRoom newMeetingRoom(
            String openviduSessionName,
            String[] openviduTokens,
            String sessionId1,
            String sessionId2,
            String mbti1,
            String mbti2) {
        LocalDateTime now = LocalDateTime.now();
        return MeetingRoom.builder()
                .openviduSessionName(openviduSessionName)
                .openviduTokens(openviduTokens)
                .sessionIds(new String[]{sessionId1, sessionId2})
                .meetingRoomStatus(new Boolean[]{true, true})
                .meetingRoomResults(new AnalysisRegisterRequest[]{
                        AnalysisRegisterRequest.builder()
                                .fromMbti(mbti1)
                                .toMbti(mbti2)
                                .startTime(now)
                                .friendResult(false)
                                .build(),
                        AnalysisRegisterRequest.builder()
                                .fromMbti(mbti2)
                                .toMbti(mbti1)
                                .startTime(now)
                                .friendResult(false)
                                .build()})
                .build();
    }
}
