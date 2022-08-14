package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.response.FriendResponse;
import com.ssafy.mbting.api.service.FriendService;
import com.ssafy.mbting.api.service.MemberService;
import com.ssafy.mbting.common.util.BaseResponseUtil;
import com.ssafy.mbting.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/friend")
public class FriendController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BaseResponseUtil baseResponseUtil;
    private final MemberService memberService;
    private final FriendService friendService;

    // 친구 리스트 조회
    @GetMapping("/")
    @Transactional
    public ResponseEntity<?> list(@RequestParam(value = "email") String email,
                                  @RequestParam(value = "nickname", required = false) String nickname,
                                  @RequestParam(value = "mbti", required = false) String mbti) {
        Member member = memberService.getUserByEmail(email);
        if (nickname != null) {
            return baseResponseUtil.success(FriendResponse.builder()
                    .friends(friendService.getFriendByNickname(member, nickname))
                    .build());
        } else if (mbti != null) {
            return baseResponseUtil.success(FriendResponse.builder()
                    .friends(friendService.getFriendListByMbti(member, mbti))
                    .build());
        }
        return baseResponseUtil.success(FriendResponse.builder()
                .friends(friendService.getFriendList(member))
                .build());
    }

    // 친구 추가
    @PostMapping("/{fromEmail}/{toEmail}") // Todo: 화상 미팅룸에서 호출하게 해야 함
    public ResponseEntity<?> create(@PathVariable("fromEmail") String fromEmail, @PathVariable("toEmail") String toEmail) {
        logger.debug("여기까지 왔니? -0");
        Member fromMember = memberService.getUserByEmail(fromEmail);
        Member toMember = memberService.getUserByEmail(toEmail);
        logger.debug("여기까지 왔니? -1");
        friendService.createFriend(fromMember, toMember);
        logger.debug("여기까지 왔니? -2");
        return baseResponseUtil.success();
    }

    // 친구 삭제
    @DeleteMapping("/{fromEmail}/{toEmail}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("fromEmail") String fromEmail, @PathVariable("toEmail") String toEmail) {
        Member fromMember = memberService.getUserByEmail(fromEmail);
        Member toMember = memberService.getUserByEmail(toEmail);
        friendService.deleteFriend(fromMember, toMember);

        return baseResponseUtil.success();
    }
}
