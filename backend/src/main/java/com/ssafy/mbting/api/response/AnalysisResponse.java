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
    Integer totalCount; //매칭 총한 횟수
    Integer proposalCount; // 매칭 카드로 보고 선택한 횟수
    Integer voiceCount; //음성까지 간 횟수
    Integer friendCount; // 친구까지 성사 된 횟수
    //음성 미팅한 시간
    //화상 미팅한 시간
}
