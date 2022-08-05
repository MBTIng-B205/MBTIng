package com.ssafy.mbting;

import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.FriendRepository;
import com.ssafy.mbting.db.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FriendTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FriendRepository friendRepository;
    @Test
    void friendTest(){

        // data generate
        for (int i = 0; i < 10; i++){
            Member m1 = new Member();
            m1.setNickname("손님" + i);
            m1.setEmail("test" + i + "@test.com");
            m1.setGender(true);
            m1.setMbti("ISFJ");
            memberRepository.save(m1);
            Member m2 = new Member();
            m2.setNickname("정훈" + i);
            m2.setEmail("hun" + i + "@test.com");
            m2.setGender(false);
            m2.setMbti("ENTP");
            memberRepository.save(m2);
            Friend friend = new Friend(m1, m2);
            friendRepository.save(friend);
        }
        for (int i = 0; i < 10; i++){
            String email = "test" + i + "@test.com";
            Member m1 = memberRepository.findByEmail(email);
            for (int j = i + 1; j < 10; j++){
                String email2 = "hun" + j + "test.com";
                Member m2 = memberRepository.findByEmail(email2);
                Friend friend = new Friend(m1, m2);
                friendRepository.save(friend);
            }
        }




    }
}
