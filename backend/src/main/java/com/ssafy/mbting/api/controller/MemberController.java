package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import com.ssafy.mbting.api.request.MemberUpdateRequest;
import com.ssafy.mbting.api.response.MemberRegisterResponse;
import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.api.service.InterestMemberService;
import com.ssafy.mbting.api.service.InterestService;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.auth.MemberDetails;
import com.ssafy.mbting.common.util.BaseResponseUtil;
import com.ssafy.mbting.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;
    private final ResourceLoader resLoader;
    private final BaseResponseUtil baseResponseUtil;
    private final InterestService interestService;
    private final InterestMemberService interestMemberService;

    @PostMapping()
    @Transactional
    public ResponseEntity<?> register(@RequestBody MemberRegisterRequest registerInfo) {
        Member member = memberService.getUserByEmail(registerInfo.getEmail());
        if (member.getDeleted() == true) {
            member.setNickname(registerInfo.getNickname());
            member.setGender(registerInfo.getGender());
            member.setBirth(registerInfo.getBirth());
            member.setMbti(registerInfo.getMbti());
            member.setProfileUrl(registerInfo.getProfileUrl());
            interestMemberService.deleteAllByMember(member);
            member.setInterestMember(interestService.insertInterest(registerInfo.getInterests(), member));
            member.setDeleted(false);
        } else {
            member = memberService.createMember(registerInfo);
            member.setInterestMember(interestService.insertInterest(registerInfo.getInterests(), member));
        }
        return baseResponseUtil.success(MemberRegisterResponse.builder()
                .member(MemberResponse.of(member))
                .build());
    }

    @GetMapping("/me")
    @Transactional
    public ResponseEntity<?> getMemberInfo(Authentication authentication) {
        MemberDetails userDetails = (MemberDetails) authentication.getDetails();
        if (userDetails == null)
            return ResponseEntity.badRequest().build();
        Member member = userDetails.getMember();
        member.setInterestMember(interestService.getInterest(member));
        return baseResponseUtil.success(MemberResponse.of(userDetails.getMember()));
    }

    @PutMapping()
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateRequest updateInfo) {
        Member member = memberService.updateMember(updateInfo);

        return baseResponseUtil.success(MemberRegisterResponse.builder()
                .member(MemberResponse.of(member))
                .build());
    }

    @GetMapping("/")
    public ResponseEntity<?> validCheck(@RequestParam(value = "nickname") String nickname) {
        return baseResponseUtil.success(memberService.nicknameValid(nickname));
    }

    @PostMapping("userprofile/{email}")
    public ResponseEntity<?> userProfile(@PathVariable("email") String email, @RequestParam("upfile") MultipartFile file) {
        Member member = memberService.getUserByEmail(email);
        try {
            if (!file.isEmpty()) {
                Resource res = resLoader.getResource("classpath:static/upload");
                String canonicalPath = res.getFile().getCanonicalPath();
                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = canonicalPath + File.separator + today;
                File folder = new File(saveFolder);
                System.out.println("folder.toString() = " + folder.toString());
                if (!folder.exists())
                    folder.mkdirs();
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
                UUID uuid = UUID.randomUUID();
                String filename = uuid.toString() + extension;

                file.transferTo(new File(folder, filename));
                member.setProfileUrl("http://localhost:8080/static/upload/" + today + "/" + filename);
                memberService.updateMember(MemberUpdateRequest.of(member));
            }
        } catch (Exception e) {
            System.out.println(e);
            return baseResponseUtil.fail("file upload fail");
        }
        return baseResponseUtil.success(MemberRegisterResponse.builder()
                .member(MemberResponse.of(member))
                .build());
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestParam(value = "email") String email) {

        return baseResponseUtil.success(memberService.deleteMember(email));
    }
}
