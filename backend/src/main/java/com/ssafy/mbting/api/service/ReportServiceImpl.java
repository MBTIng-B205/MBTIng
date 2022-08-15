package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.ReportRegisterRequest;
import com.ssafy.mbting.api.response.ReportResponse;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Report;
import com.ssafy.mbting.db.entity.ReportType;
import com.ssafy.mbting.db.repository.MemberRepository;
import com.ssafy.mbting.db.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    @Override
    public Report createReport(ReportRegisterRequest reportRegisterRequest) {
        Member reporter =memberRepository.findByEmailAndDeleted(reportRegisterRequest.getFrom_id(),false);
        Member reported = memberRepository.findByEmailAndDeleted(reportRegisterRequest.getTo_id(),false);
        Report report = new Report();
        report.setFrom_id(reporter);
        report.setTo_id(reported);
        report.setContent(reportRegisterRequest.getContent());
        report.setType(ReportType.UNRESOLVED);
        return reportRepository.save(report);
    }
}
