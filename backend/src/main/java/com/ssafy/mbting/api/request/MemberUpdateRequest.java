package com.ssafy.mbting.api.request;

import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.db.entity.Interest;
import com.ssafy.mbting.db.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@Builder
//@ApiModel("UserRegisterPostRequest")
public class MemberUpdateRequest {
    private String email;
    private String nickname;
    //private boolean gender;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    //private LocalDate birth;
    private String sido;
    private String mbti;
    private String profileUrl;
    @Builder.Default
    private List<Interest> interests = new ArrayList<>();


    public static MemberUpdateRequest of(Member member) {
        return MemberUpdateRequest.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .sido(member.getSido())
                .mbti(member.getMbti())
                .profileUrl(member.getProfileUrl())
                .build();
    }

    @Override
    public String toString() {
        return "MemberUpdateRequest{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sido='" + sido + '\'' +
                ", mbti='" + mbti + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", interests=" + interests +
                '}';
    }
}
