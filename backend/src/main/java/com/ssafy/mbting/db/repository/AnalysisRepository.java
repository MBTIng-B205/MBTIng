package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis,Long> {
    Integer countAllByFromMbtiAndToMbti(String fromMbti, String toMbti);
    Integer countAllByFromMbtiAndToMbtiAndProposalResult(String fromMbti, String toMbti, Boolean proposalResult);
    Integer countAllByFromMbtiAndToMbtiAndVoiceResult(String fromMbti, String toMbti, Boolean voiceResult);
    Integer countAllByFromMbtiAndToMbtiAndFriendResult(String fromMbti, String toMbti, Boolean friendResult);
}
