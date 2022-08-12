package com.ssafy.mbting.api.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRegisterRequest {

    public enum VoiceResult {
        GREEN, RED, TIMEOUT
    }

    String fromMbti;
    String toMbti;
    Boolean proposalResult;
    VoiceResult voiceResult;
    Boolean friendResult;
    LocalDateTime startTime;
    LocalDateTime middleTime;
    LocalDateTime endTime;
}
