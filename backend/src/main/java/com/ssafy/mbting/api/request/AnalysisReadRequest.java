package com.ssafy.mbting.api.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisReadRequest {
    String fromMbti;
    String toMbti;
}
