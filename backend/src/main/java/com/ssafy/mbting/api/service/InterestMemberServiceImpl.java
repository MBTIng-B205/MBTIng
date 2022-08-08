package com.ssafy.mbting.api.service;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.InterestMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("interestMemberService")
@RequiredArgsConstructor
public class InterestMemberServiceImpl implements InterestMemberService{
    private final InterestMemberRepository interestMemberRepository;
    @Override
    public boolean deleteAllByMember(Member member) {
        try {
            interestMemberRepository.deleteAllByMember(member);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
