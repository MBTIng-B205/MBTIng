package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.enums.Gender;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

	private String email;
	private String nickname;
	private Gender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String sido;
	private String mbti;
	private String profileUrl;
	@Builder.Default
	private List<String> interests = new ArrayList<>();

	public static MemberResponse of(Member member) {
		List<String> newInterests;
		try {
		List<InterestMember> interestMembers = member.getInterestMember();
			newInterests = new ArrayList<>();
			for (InterestMember i : interestMembers) {
				newInterests.add(i.getInterest().getIname());
			}
		}
		catch (Exception e){
			newInterests =null;
		}

		return MemberResponse.builder()
				.email(member.getEmail())
				.nickname(member.getNickname())
				.gender(member.getGender())
				.birth(member.getBirth())
				.sido(member.getSido())
				.mbti(member.getMbti())
				.profileUrl(member.getProfileUrl())
				.interests(newInterests)
				.build();
	}
}
