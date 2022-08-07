package com.ssafy.mbting.db.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestMember extends BaseEntity {

    @ManyToOne(
            fetch = LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "interest_id")
    private Interest interest;
    @ManyToOne(
            fetch = LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "member_id")
    private Member member;

    public static InterestMember of(Interest interest, Member member){
        return InterestMember.builder().interest(interest).member(member).build();
    }
}