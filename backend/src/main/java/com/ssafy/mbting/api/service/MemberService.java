package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.db.entity.Member;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface MemberService {
	Member createUser(MemberRegisterRequest userRegisterInfo);
	Member getUserByEmail(String userId);
}
