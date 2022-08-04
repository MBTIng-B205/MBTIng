package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.ReportRegisterRequest;
import com.ssafy.mbting.api.response.*;
import com.ssafy.mbting.api.service.ReportService;
import com.ssafy.mbting.db.entity.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping()
    public ResponseEntity<?> register(
            @RequestBody
                    ReportRegisterRequest reportRegisterRequest) {

        Report report = reportService.createReport(reportRegisterRequest);

        return ResponseEntity.ok().body(ReportRegisterResponse.builder()
                .report(ReportResponse.of(report))
                .build());
    }
}
