package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class Member extends BaseEntity {

    private String email;
    private String nickname;
    private boolean gender;
    private LocalDateTime birth;
    private String sido;
    private String mbti;
    private String profileUrl;


    @OneToMany(mappedBy = "member")
    private List<Interest> interests = new ArrayList<>();
    @OneToMany(mappedBy = "toId")
    private List<Message> messages = new ArrayList<>();
    @OneToMany(mappedBy = "toId")
    private List<Friend> friends = new ArrayList<>();

}
