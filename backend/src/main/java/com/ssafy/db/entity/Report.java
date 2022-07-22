package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Report extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_id")
    private Member from_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_id")
    private Member to_id;

    private String content;
    private LocalDateTime reportTime;

    private ReportType type;

}

