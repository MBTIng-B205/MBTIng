package com.ssafy.mbting.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MbtiInfo extends BaseEntity {

    private String description;
}
