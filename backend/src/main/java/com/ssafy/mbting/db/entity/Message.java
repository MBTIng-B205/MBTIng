package com.ssafy.mbting.db.entity;

import com.ssafy.mbting.api.response.MessageResponse;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    LocalDateTime sendTime;
    @NotNull
    private Boolean readByTo = false;
    @NotNull
    private Boolean deletedByTo = false;
    @NotNull
    private Boolean deletedByFrom = false;
}
