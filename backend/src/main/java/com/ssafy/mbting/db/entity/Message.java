package com.ssafy.mbting.db.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private LocalDateTime sendTime;
    @Column(columnDefinition = "TINYINT(1)")
    @NotNull
    private boolean readByTo;
    @Column(columnDefinition = "TINYINT(1)")
    @NotNull
    private boolean deletedByTo;
    @Column(columnDefinition = "TINYINT(1)")
    @NotNull
    private boolean deletedByFrom;

    @PrePersist
    public void sendTime() {
        this.sendTime = LocalDateTime.now();
    }
}
