package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.AnalysisReadRequest;
import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import com.ssafy.mbting.api.response.AnalysisResponse;
import com.ssafy.mbting.db.entity.Analysis;
import com.ssafy.mbting.db.repository.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisSeriviceImpl implements AnalysisService {
    private final AnalysisRepository analysisRepository;

    public void addAnalysis(AnalysisRegisterRequest analysisRegisterRequest){
        analysisRepository.save(Analysis.of(analysisRegisterRequest));
    }

    public AnalysisResponse getAnalysis(AnalysisReadRequest analysisReadRequest){
        String fromMbti = analysisReadRequest.getFromMbti();
        String toMbti = analysisReadRequest.getToMbti();
        int totalCount = analysisRepository.countAllByFromMbtiAndToMbti(fromMbti,toMbti);
        int proposalCount = analysisRepository.countAllByFromMbtiAndToMbtiAndProposalResult(fromMbti,toMbti,true);
        int voiceCount = analysisRepository.countAllByFromMbtiAndToMbtiAndVoiceResult(fromMbti,toMbti,true);
        int friendCount = analysisRepository.countAllByFromMbtiAndToMbtiAndFriendResult(fromMbti,toMbti,true);
        return AnalysisResponse.builder()
                .fromMbti(analysisReadRequest.getFromMbti())
                .toMbti(analysisReadRequest.getToMbti())
                .totalCount(totalCount)
                .proposalCount(proposalCount)
                .voiceCount(voiceCount)
                .friendCount(friendCount)
                .build();
    }
}