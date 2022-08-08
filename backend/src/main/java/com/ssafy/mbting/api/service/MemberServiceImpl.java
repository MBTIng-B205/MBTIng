package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.MemberUpdateRequest;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.InterestMemberRepository;
import com.ssafy.mbting.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final MemberRepository memberRepository;
	private final InterestService interestService;
	private final InterestMemberService interestMemberService;

	@Override
	public Member createMember(MemberRegisterRequest userRegisterInfo) {
		Member member = Member.of(userRegisterInfo);
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		return memberRepository.save(member);
	}

	@Override
	public Member getUserByEmail(String email) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		return memberRepository.findByEmail(email);

	}

	@Transactional
	@Override
	public Member updateMember(MemberUpdateRequest userRegisterInfo ) {
		Member updatemember =memberRepository.findByEmail(userRegisterInfo.getEmail());


		interestMemberService.deleteAllByMember(updatemember);
		interestService.insertInterest(userRegisterInfo.getInterests(), updatemember);


		updatemember.setNickname(userRegisterInfo.getNickname());
		updatemember.setMbti(userRegisterInfo.getMbti());

		updatemember.setSido(userRegisterInfo.getSido());
		//updatemember.setProfileUrl(userRegisterInfo.getProfileUrl());
		return updatemember;
	}

	@Override
	@Transactional
	public boolean deleteMember(String email) {
		Member member = memberRepository.findByEmail(email);
		member.setDeleted(true);
		return true;
	}

	@Override
	public boolean nicknameValid(String nickname) {
		if(memberRepository.countAllByNicknameAndDeleted(nickname, false) != 0){
			return false;
		}
		return true;
	}

}