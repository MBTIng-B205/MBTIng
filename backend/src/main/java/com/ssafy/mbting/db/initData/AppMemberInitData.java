package com.ssafy.mbting.db.initData;

import com.ssafy.mbting.db.entity.Interest;
import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.db.repository.InterestMemberRepository;
import com.ssafy.mbting.db.repository.InterestRepository;
import com.ssafy.mbting.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppMemberInitData {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;
    private final InterestMemberRepository interestMemberRepository;

    public void create() {
        List<Member> devs = new ArrayList<>();
        Member d1 = Member.builder()
                .nickname("이기진")
                .email("rlwls1101@hanmail.net")
                .gender(Gender.MALE)
                .mbti("INTP")
                .birth(LocalDate.of(1995, 4, 3))
                .sido("경기")
                .build();
        Member d2 = Member.builder()
                .nickname("이예은")
                .email("ejrtks261@nate.com")
                .gender(Gender.MALE)
                .mbti("INFJ")
                .birth(LocalDate.of(1998, 8,28))
                .sido("인천")
                .build();
        Member d3 = Member.builder()
                .nickname("장정훈")
                .email("hun950803@gmail.com")
                .gender(Gender.MALE)
                .mbti("ISFJ")
                .birth(LocalDate.of(1995, 8, 3))
                .sido("대전")
                .build();
        Member d4 = Member.builder()
                .nickname("최수연")
                .email("c.martin20222023@gmail.com")
                .gender(Gender.FEMALE)
                .mbti("INFJ")
                .birth(LocalDate.of(1995, 10, 13))
                .sido("경기")
                .build();
        Member d5 = Member.builder()
                .nickname("배건길")
                .email("wmf3362@naver.com")
                .gender(Gender.MALE)
                .mbti("INTJ")
                .birth(LocalDate.of(1994, 2, 5))
                .sido("대전")
                .build();
        Member d6 = Member.builder()
                .nickname("송제영")
                .email("wp29dud@naver.com")
                .gender(Gender.MALE)
                .mbti("ISTP")
                .birth(LocalDate.of(1994, 2, 9))
                .sido("대전")
                .build();

        devs.add(d1);
        devs.add(d2);
        devs.add(d3);
        devs.add(d4);
        devs.add(d5);
        devs.add(d6);

        memberRepository.saveAll(devs);

        List<Interest> interests = new ArrayList<>();

        Interest i1 = Interest.builder()
                .iname("Java")
                .build();
        Interest i2 = Interest.builder()
                .iname("JavaScript")
                .build();
        Interest i3 = Interest.builder()
                .iname("VSCode")
                .build();
        Interest i4 = Interest.builder()
                .iname("Spring")
                .build();
        Interest i5 = Interest.builder()
                .iname("Vue")
                .build();
        Interest i6 = Interest.builder()
                .iname("OpenVidu")
                .build();

        interests.add(i1);
        interests.add(i2);
        interests.add(i3);
        interests.add(i4);
        interests.add(i5);
        interests.add(i6);

        interestRepository.saveAll(interests);

        List<InterestMember> interestMembers = new ArrayList<>();

        devs.forEach((dev) -> {
            interests.forEach((interest -> {
                interestMembers.add(InterestMember.builder()
                        .interest(interest)
                        .member(dev)
                        .build());
            }));
        });

        interestMemberRepository.saveAll(interestMembers);
    }
}
