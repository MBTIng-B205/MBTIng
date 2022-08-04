package com.ssafy.mbting.db.entity;

import com.ssafy.mbting.api.request.MemberRegisterRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member extends BaseEntity{
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String nickname;
    private boolean gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String sido;
    private String mbti;
    private String profileUrl;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Interest> interests = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "toId")
    private List<Message> messages = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "toId")
    private List<Friend> friends = new ArrayList<>();

    public static Member of(MemberRegisterRequest memberRegisterRequest) {
        return Member.builder()
                .email(memberRegisterRequest.getEmail())
                .nickname(memberRegisterRequest.getNickname())
                .gender(memberRegisterRequest.isGender())
                .birth(memberRegisterRequest.getBirth())
                .sido(memberRegisterRequest.getSido())
                .mbti(memberRegisterRequest.getMbti())
                .profileUrl(memberRegisterRequest.getProfileUrl())
                .build();
    }
}
