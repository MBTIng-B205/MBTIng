package com.ssafy.mbting.db.entity;

import com.ssafy.mbting.api.request.ReportRegisterRequest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BaseEntity{

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

    public static Report of(ReportRegisterRequest reportRegisterRequest) {
        return Report.builder().from_id(reportRegisterRequest.getFrom_id())
                .to_id(reportRegisterRequest.getTo_id())
                .content(reportRegisterRequest.getContent())
                .reportTime(reportRegisterRequest.getReportTime())
                .type(reportRegisterRequest.getType())
                .build();
    }
}

