package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.MemberUpdateRequest;
import com.ssafy.mbting.db.entity.Member;
import org.springframework.transaction.annotation.Transactional;


public interface MemberService {
	Member createMember(MemberRegisterRequest userRegisterInfo);
	Member getUserByEmail(String userId);

	@Transactional
	Member updateMember(MemberUpdateRequest userRegisterInfo);

	boolean deleteMember(String email);

	boolean nicknameValid(String nickname);
}
