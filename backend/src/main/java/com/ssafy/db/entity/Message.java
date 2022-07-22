package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_id")
    private Member fromId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_id")
    private Member toId;
    private String content;
    private LocalDateTime sendTime;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("0")
    private boolean readcheck;
}

