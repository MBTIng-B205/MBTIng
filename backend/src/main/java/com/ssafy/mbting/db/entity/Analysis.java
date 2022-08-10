package com.ssafy.mbting.db.entity;

import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Analysis extends BaseEntity {
    String fromMbti;
    String toMbti;
    Boolean proposalResult;
    Boolean voiceResult;
    Boolean friendResult;
    Period voiceTime;
    Period videoTime;

    public static Analysis of(AnalysisRegisterRequest analysisRegisterRequest){
        return  Analysis.builder()
                .fromMbti(analysisRegisterRequest.getFromMbti())
                .toMbti(analysisRegisterRequest.getToMbti())
                .proposalResult(analysisRegisterRequest.getProposalResult())
                .voiceResult(analysisRegisterRequest.getVoiceResult())
                .friendResult(analysisRegisterRequest.getFriendResult())
                .build();
    }
}
