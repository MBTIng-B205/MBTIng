package com.ssafy.mbting.api.service;

import com.ssafy.mbting.db.entity.Interest;
import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.InterestMemberRepository;
import com.ssafy.mbting.db.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("interestService")
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final InterestMemberRepository interestMemberRepository;
    private final InterestRepository interestRepository;

    @Override
    public List<InterestMember> getInterest(Member member) {
        return interestMemberRepository.findAllByMember(member);
    }

    @Override
    public List<InterestMember> insertInterest(List<String> interests, Member member) {
        List<InterestMember> interestMembers = new ArrayList<>();
        for (String i : interests){
            Interest interest = interestRepository.findByIname(i);
            if(interest == null){
                interest = Interest.of(i);
                interestRepository.save(interest);
            }
            interestMembers.add(interestMemberRepository.save(InterestMember.of(interest, member)));
        }
        return interestMembers;
    }
}