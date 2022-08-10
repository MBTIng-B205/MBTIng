package com.ssafy.mbting.ws.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class MeetingUserTargetTable {

    private static final int GENDER_SCORE = 100;
    private static final int INTEREST_SCORE = 1;
    private final Map<Long, Integer> targetUserIdScoreTable = Maps.newHashMap();
    private final Map<Integer, Set<Long>> scoreTargetUserIdSetMap = Maps.newHashMap();

    public void addGenderScore(Long targetUserId) {
        targetUserIdScoreTable.computeIfAbsent(targetUserId, l1 -> {
            scoreTargetUserIdSetMap.computeIfAbsent(GENDER_SCORE, l2 -> {
                Set<Long> newSet = Sets.newHashSet();
                newSet.add(targetUserId);
                return newSet;
            });
            scoreTargetUserIdSetMap.computeIfPresent(GENDER_SCORE, (l2, oldSet) -> {
                oldSet.add(targetUserId);
                return oldSet;
            });
            return GENDER_SCORE;
        });
        targetUserIdScoreTable.computeIfPresent(targetUserId, (key, oldValue) -> oldValue + GENDER_SCORE);
    }

    public void addInterestScore(Long targetUserId) {
        targetUserIdScoreTable.putIfAbsent(targetUserId, INTEREST_SCORE);
        targetUserIdScoreTable.computeIfPresent(targetUserId, (key, oldValue) -> oldValue + INTEREST_SCORE);
    }

    public Long getBestTarget() {
        return null;
    }
}
