package com.ssafy.mbting.api.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRegisterRequest {

    String fromMbti;
    String toMbti;
    Boolean proposalResult;
    AudioStageResult voiceResult;
    Boolean friendResult;
    LocalDateTime startTime;
    LocalDateTime middleTime;
    LocalDateTime endTime;
}
