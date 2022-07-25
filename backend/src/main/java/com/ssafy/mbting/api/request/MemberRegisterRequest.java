package com.ssafy.mbting.api.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
//@ApiModel("UserRegisterPostRequest")
public class MemberRegisterRequest {
	private String email;
	private String nickname;
	private boolean gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String sido;
	private String mbti;
	private String profileUrl;
}
