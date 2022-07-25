package com.ssafy.mbting.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MbtiInfo extends BaseEntity {


    private String description;

}

