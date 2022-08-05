package com.ssafy.mbting.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

public class InterestMember extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
