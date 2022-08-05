package com.ssafy.mbting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestMember extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static InterestMember of(Interest interest, Member member){
        return InterestMember.builder().interest(interest).member(member).build();
    }
}