package com.ssafy.mbting.db.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseEntity {

    private String content;
    @CreationTimestamp
    private LocalDateTime sendTime;
    @Builder.Default
    @NotNull
    private Boolean tofriendflag = false;
    @Builder.Default
    @NotNull
    private Boolean fromfriendflag = false;
    @Builder.Default
    @NotNull
    private Boolean readByTo = false;
    @Builder.Default
    @NotNull
    private Boolean deletedByTo = false;
    @Builder.Default
    @NotNull
    private Boolean deletedByFrom = false;
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
}
