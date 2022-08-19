package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestMemberRepository extends JpaRepository<InterestMember, Long> {
    
    List<InterestMember> findAllByMember(Member member);
    void deleteAllByMember(Member member);
}
