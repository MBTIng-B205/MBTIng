package com.ssafy.mbting.ws.model.algorithm;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@Builder
public class MatchingScoreBoard {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<String, Integer> idScoreMap = newHashMap();
    private final Map<Integer, Set<String>> scoreIdSetMap = newHashMap();

    private int genderScore;
    private int sidoScore;
    private int interestScore;

    public void addGenderScore(String id) {
        addScore(id, genderScore);
    }

    public void addSidoScore(String id) {
        addScore(id, sidoScore);
    }

    public void addInterestScore(String id) {
        addScore(id, interestScore);
    }

    public String getBestTarget() {
        Map.Entry<Integer, Set<String>> entry = scoreIdSetMap.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getKey))
                .orElseThrow(() -> new RuntimeException("Internal Server Error!"));
        logger.debug("매칭 알고리즘 결과:\n최고점: {}\n상대 후보 Session IDs: {}\n한 명이 아니라면 이 중 아무나 나갑니다."
                , entry.getKey()
                , entry.getValue());
        return entry.getValue().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Internal Server Error!"));
    }

    private void addScore(String id, int score) {
        int oldScore = idScoreMap.get(id);

        idScoreMap.computeIfPresent(id, (i, s) -> s + score);
        idScoreMap.putIfAbsent(id, score);

        int newScore = idScoreMap.get(id);

        Set<String> oldIdSet = scoreIdSetMap.getOrDefault(oldScore, newHashSet());
        oldIdSet.remove(id);
        if (oldIdSet.isEmpty()) scoreIdSetMap.remove(oldScore);

        scoreIdSetMap.computeIfPresent(newScore, (s, ids) -> {
            ids.add(id);
            return ids;
        });
        scoreIdSetMap.computeIfAbsent(newScore, s -> {
            Set<String> set = newHashSet();
            set.add(id);
            return set;
        });

        logger.debug("\n\n후보 {} 에게 {} 점을 추가한 결과:\n[id, score]: {}\n[score,ids]: {}\n"
                , id
                , score
                , idScoreMap
                , scoreIdSetMap);
    }
}
