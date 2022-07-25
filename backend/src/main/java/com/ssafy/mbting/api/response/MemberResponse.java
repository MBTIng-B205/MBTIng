package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("UserResponse")
public class MemberResponse {
//	@ApiModelProperty(name="User ID")
	private String email;
	private String nickname;
	private boolean gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String sido;
	private String mbti;
	private String profileUrl;

	public static MemberResponse of(Member member) {
		return MemberResponse.builder()
				.email(member.getEmail())
				.nickname(member.getNickname())
				.gender(member.isGender())
				.birth(member.getBirth())
				.sido(member.getSido())
				.mbti(member.getMbti())
				.profileUrl(member.getProfileUrl())
				.build();
	}
}
