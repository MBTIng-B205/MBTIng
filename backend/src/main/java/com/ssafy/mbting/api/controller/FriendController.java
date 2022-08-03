package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.response.FriendResponse;
import com.ssafy.mbting.api.service.FriendService;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.util.BaseResponseUtil;
import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/friend")
public class FriendController {

    private static BaseResponseUtil baseResponseUtil;
    private static MemberService memberService;
    private static FriendService friendService;

    // 친구 리스트 조회
    @GetMapping("/{email}")
    public ResponseEntity<?> list(@PathVariable("email") String email) {
        Member member = memberService.getUserByEmail(email);

        return baseResponseUtil.success(FriendResponse.builder()
                .friends(friendService.getFriendList(member))
                .build());
    }

    // 친구 추가
    @PostMapping("/{fromEmail}/{toEmail}")
    public ResponseEntity<?> create(@PathVariable("fromEmail") String fromEmail, @PathVariable("toEmail") String toEmail){
        Member fromMember = memberService.getUserByEmail(fromEmail);
        Member toMember = memberService.getUserByEmail(toEmail);
        friendService.createFriend(fromMember, toMember);

        return baseResponseUtil.success();
    }

    // 친구 삭제
    @DeleteMapping("/{fromEmail}/{toEmail}")
    public ResponseEntity<?> delete(@PathVariable("fromEmail") String fromEmail, @PathVariable("toEmail") String toEmail) {
        Member fromMember = memberService.getUserByEmail(fromEmail);
        Member toMember = memberService.getUserByEmail(toEmail);
        friendService.deleteFriend(fromMember, toMember);

        return baseResponseUtil.success();
    }
    // 친구 검색
}
