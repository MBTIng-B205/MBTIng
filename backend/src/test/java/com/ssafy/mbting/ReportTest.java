package com.ssafy.mbting;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.ReportRegisterRequest;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.api.service.ReportService;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.ReportType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ReportTest {

    @Autowired
    MemberService mbr;

    @Autowired
    ReportService reportService;
    @Test
    void sendReport() {

        MemberRegisterRequest mb1 = new MemberRegisterRequest();
        mb1.setNickname("홍길동123");
        mb1.setGender(true);
        MemberRegisterRequest mb2 = new MemberRegisterRequest();
        mb2.setNickname("유관순123");
        mb2.setGender(false);


        mb1.setEmail("rlwls11011@hamail.net");
        mb2.setEmail("rlwl20211@gmail.com");

        System.out.println("mb1 = " + mb1);
        System.out.println("mb2 = " + mb2);

        Member member1 = mbr.createMember(mb1);
        Member member2 = mbr.createMember(mb2);
        ReportRegisterRequest reportRegisterRequest = new ReportRegisterRequest();
        //reportRegisterRequest.setTo_id(member1);

        //reportRegisterRequest.setFrom_id(member2);
        reportRegisterRequest.setContent("신고간다잉");
        //reportRegisterRequest.setType(ReportType.UNRESOLVED);

        reportService.createReport(reportRegisterRequest);

    }
}
