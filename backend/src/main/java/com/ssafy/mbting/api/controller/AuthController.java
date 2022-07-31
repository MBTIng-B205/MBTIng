package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.response.MemberLoginResponse;
import com.ssafy.mbting.common.util.KakaoAPI;
import com.ssafy.mbting.db.entity.Member;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.util.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api")
//auth/login
public class AuthController {

	public Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
    MemberService memberService;
	KakaoAPI kakaoApi = new KakaoAPI();
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam("code") String code) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		logger.debug("code "+ code);
		// 1번 인증코드 요청 전달

		String accessToken = kakaoApi.getAccessToken(code);

		logger.debug("accessToken : "+accessToken);
		// 2번 인증코드로 토큰 전달
		HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

		logger.debug("login info: " + userInfo.toString());

		String token = JwtTokenUtil.getToken((String) userInfo.get("email"));

		if(userInfo.get("email") != null) {

			String msg = "login success";
			Member member = memberService.getUserByEmail((String) userInfo.get("email"));
			if(member == null){
				return ResponseEntity.accepted().body(MemberLoginResponse.builder()
						.visited(false)
						.jwt(token)
						.build());
			}
			logger.debug("여기서걸림");
			logger.debug("{}", userInfo);
			logger.debug("token = " + token);
		}else {
			logger.debug("이메일이 안 왔다");
			logger.debug("우선순위 낮은 우리의 숙제~~~~~~");
			return ResponseEntity.unprocessableEntity().build();
		}
		return ResponseEntity.accepted().body(MemberLoginResponse.builder()
				.visited(true)
				.jwt(token)
				.build());
	}
}
