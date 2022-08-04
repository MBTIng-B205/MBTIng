package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.ReportRegisterRequest;
import com.ssafy.mbting.db.entity.Report;
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

    public Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportRepository reportRepository;

    @Override
    public Report createReport(ReportRegisterRequest reportRegisterRequest) {
        Report report = Report.of(reportRegisterRequest);
        return reportRepository.save(report);
    }
}
