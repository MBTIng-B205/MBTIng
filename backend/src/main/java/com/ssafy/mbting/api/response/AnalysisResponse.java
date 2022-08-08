package com.ssafy.mbting.api.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {
    String fromMbti;
    String toMbti;
    Integer totalCount;
    Integer proposalCount;
    Integer voiceCount;
    Integer friendCount;
}
