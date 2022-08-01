package com.ssafy.mbting.db.entity;

import com.ssafy.mbting.api.request.ReportRegisterRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
public class Report extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_id")
    private Member from_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_id")
    private Member to_id;

    private String content;

    @CreationTimestamp
    private LocalDateTime reportTime;

    private ReportType type;

    public static Report of(ReportRegisterRequest reportRegisterRequest) {
        return Report.builder().from_id(reportRegisterRequest.getFrom_id())
                .to_id(reportRegisterRequest.getTo_id())
                .content(reportRegisterRequest.getContent())
                .reportTime(reportRegisterRequest.getReportTime())
                .type(reportRegisterRequest.getType())
                .build();
    }
}

