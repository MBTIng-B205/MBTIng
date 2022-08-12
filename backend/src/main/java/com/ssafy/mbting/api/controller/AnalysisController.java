package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.AnalysisReadRequest;
import com.ssafy.mbting.api.service.AnalysisService;
import com.ssafy.mbting.common.util.BaseResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;
    private final BaseResponseUtil baseResponseUtil;
    @PostMapping()
    public ResponseEntity<?> getResult(@RequestBody AnalysisReadRequest analysisReadRequest){
        return baseResponseUtil.success(analysisService.getAnalysis(analysisReadRequest));
    }

}
