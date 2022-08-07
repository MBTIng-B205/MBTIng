package com.ssafy.mbting.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend extends BaseEntity {

    @ManyToOne(
            fetch = FetchType.LAZY
//            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name="from_id")
    private Member fromId;
    @ManyToOne(
            fetch = FetchType.LAZY
//            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name="to_id")
    private Member toId;

    public static Friend of(Member from, Member to){
        return Friend.builder()
                .fromId(from)
                .toId(to)
                .build();
    }
}
