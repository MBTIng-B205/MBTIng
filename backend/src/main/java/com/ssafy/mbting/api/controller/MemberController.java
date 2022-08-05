package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.MemberUpdateRequest;
import com.ssafy.mbting.api.response.MemberRegisterResponse;
import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.api.service.InterestService;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.auth.MemberDetails;
import com.ssafy.mbting.common.model.response.BaseResponse;
import com.ssafy.mbting.common.util.BaseResponseUtil;
import com.ssafy.mbting.db.entity.InterestMember;
import com.ssafy.mbting.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final MemberService memberService;
	private final ResourceLoader resLoader;
	private final BaseResponseUtil baseResponseUtil;
	private  final InterestService interestService;
	@PostMapping()

	@Transactional
	public ResponseEntity<?> register(@RequestBody	MemberRegisterRequest registerInfo) {

		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Member member = memberService.createMember(registerInfo);

		member.setInterestMember(interestService.insertInterest(registerInfo.getInterests(), member));
		return baseResponseUtil.success(MemberRegisterResponse.builder()
				.member(MemberResponse.of(member))
				.build());
	}
	
	@GetMapping("/me")
	@Transactional
	public ResponseEntity<?> getMemberInfo(
			Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		MemberDetails userDetails = (MemberDetails) authentication.getDetails();

		if (userDetails == null)
			return ResponseEntity.badRequest().build();
		Member member = userDetails.getMember();

		member.setInterestMember(interestService.getInterest(member));

		return baseResponseUtil.success(MemberResponse.of(userDetails.getMember()));
	}

	@PutMapping()
	public ResponseEntity<?> updateMember(
			@RequestBody MemberUpdateRequest updateInfo) {

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		Member member = memberService.updateMember(updateInfo);

		return baseResponseUtil.success(MemberRegisterResponse.builder()
				.member(MemberResponse.of(member))
				.build());
	}

	@GetMapping("/")
	public ResponseEntity<?> validCheck(@RequestParam(value = "nickname") String nickname){
		return baseResponseUtil.success(memberService.nicknameValid(nickname));
	}

	@PostMapping("userprofile/{email}")
	public ResponseEntity<?> userProfile(@PathVariable("email") String email, @RequestParam("upfile") MultipartFile file) {
		Member member = memberService.getUserByEmail(email);
		try {
			if(!file.isEmpty()) {
				Resource res = resLoader.getResource("classpath:static/upload");
				String canonicalPath = res.getFile().getCanonicalPath();
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = canonicalPath + File.separator + today;
				File folder = new File(saveFolder);
				System.out.println("folder.toString() = " + folder.toString());
				if (!folder.exists())
					folder.mkdirs();
				String originalFileName = file.getOriginalFilename();
				String extension  = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
				UUID uuid = UUID.randomUUID();
				String filename = uuid.toString()+extension;

				file.transferTo(new File(folder,filename));
				member.setProfileUrl("http://localhost:8080/static/upload/"+today+"/"+filename);
				memberService.updateMember(MemberUpdateRequest.of(member));

			}
		}catch (Exception e) {
			//여기 수정 필요
			e.printStackTrace();
			ResponseEntity.accepted().body(MemberRegisterResponse.builder()
					.member(MemberResponse.of(member))
					.build());
		}
		return ResponseEntity.ok().body(MemberRegisterResponse.builder()
				.member(MemberResponse.of(member))
				.build());
	}

	@DeleteMapping("/")
	public ResponseEntity<?> delete(@RequestParam(value = "email") String email){

		return baseResponseUtil.success(memberService.deleteMember(email));
	}
}
