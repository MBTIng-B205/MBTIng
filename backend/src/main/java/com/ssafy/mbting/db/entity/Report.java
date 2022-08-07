package com.ssafy.mbting.db.entity;

import com.ssafy.mbting.api.request.ReportRegisterRequest;
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
public class Report extends BaseEntity{
    @NotNull
    private String content;
    @CreationTimestamp
    private LocalDateTime reportTime;
    private ReportType type;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name="from_id")
    private Member from_id;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name="to_id")
    private Member to_id;
}

