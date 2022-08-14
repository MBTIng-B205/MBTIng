package com.ssafy.mbting;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.db.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class MemberTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void memberTest() {
        Member m1 = new Member();
        m1.setNickname("공효진");
        m1.setGender(Gender.FEMALE);
        Member m2 = new Member();
        m2.setNickname("강하늘");
        m2.setGender(Gender.MALE);

        m1.setEmail("공효진@hamail.net");
        m2.setEmail("강하늘@gmail.com");
        logger.info("\n\n공효진 : {}\n", memberRepository.save(m1).getId());
        logger.info("\n\n강하늘 : {}\n", memberRepository.save(m2).getId());

        logger.info("\n\n{}\n", memberRepository.findByEmailAndDeleted("공효진@hamail.net",false));
    }
}
