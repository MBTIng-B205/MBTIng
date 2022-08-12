package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.AnalysisReadRequest;
import com.ssafy.mbting.api.request.AnalysisRegisterRequest;
import com.ssafy.mbting.api.response.AnalysisResponse;

public interface AnalysisService {
     void addAnalysis(AnalysisRegisterRequest analysisRegisterRequest);

     AnalysisResponse getAnalysis(AnalysisReadRequest analysisReadRequest);
}
