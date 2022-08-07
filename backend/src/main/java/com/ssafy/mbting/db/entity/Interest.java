package com.ssafy.mbting.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Interest extends BaseEntity{

    private String iname;

    @Builder.Default
    @OneToMany(
            mappedBy = "interest",
            cascade = CascadeType.REMOVE
    )
    private List<InterestMember> interestMembers = new ArrayList<>();

    public static Interest of(String iname){
        return Interest.builder().iname(iname).build();
    }
}
