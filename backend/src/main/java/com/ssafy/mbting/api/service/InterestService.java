package com.ssafy.mbting.api.service;

import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;

import java.util.List;

public interface InterestService {
    List<String> getInterest(Member member);

    List<InterestMember> insertInterest(List<String> interests, Member member);
}
