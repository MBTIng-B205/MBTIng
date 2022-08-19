package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.MemberUpdateRequest;
import com.ssafy.mbting.api.request.ReportRegisterRequest;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Report;
import org.springframework.transaction.annotation.Transactional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ReportService {
	Report createReport(ReportRegisterRequest reportRegisterRequest);
}
