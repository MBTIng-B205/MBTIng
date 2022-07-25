package com.ssafy.mbting.api.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
//@ApiModel("UserLoginPostRequest")
public class MemberLoginRequest {
//	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String id;
//	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
}
