package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.MemberUpdateRequest;
import com.ssafy.mbting.db.entity.Member;
import org.springframework.transaction.annotation.Transactional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface MemberService {
	Member createMember(MemberRegisterRequest userRegisterInfo);
	Member getUserByEmail(String userId);

	@Transactional
	Member updateMember(MemberUpdateRequest userRegisterInfo);

	boolean deleteMember(String email);
}
