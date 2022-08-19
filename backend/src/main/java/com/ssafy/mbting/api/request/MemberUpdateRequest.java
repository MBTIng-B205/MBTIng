package com.ssafy.mbting.api.request;

import com.ssafy.mbting.db.entity.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    private String email;
    private String nickname;
    private String sido;
    private String mbti;
    private String profileUrl;

    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public static MemberUpdateRequest of(Member member) {
        return MemberUpdateRequest.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .sido(member.getSido())
                .mbti(member.getMbti())
                .profileUrl(member.getProfileUrl())
                .build();
    }
}
