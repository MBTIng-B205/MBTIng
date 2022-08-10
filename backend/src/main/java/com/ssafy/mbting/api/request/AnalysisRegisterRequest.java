package com.ssafy.mbting.api.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRegisterRequest {
    String fromMbti;
    String toMbti;
    Boolean proposalResult;
    Boolean voiceResult;
    Boolean friendResult;
    LocalDateTime startTime;
    LocalDateTime middleTime;
    LocalDateTime endTime;
}
