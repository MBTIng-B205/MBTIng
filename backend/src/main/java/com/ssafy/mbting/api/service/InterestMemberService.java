package com.ssafy.mbting.api.service;

import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestMemberService{
    boolean deleteAllByMember(Member member);
}
