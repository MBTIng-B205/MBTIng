package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Report;
import com.ssafy.mbting.db.entity.ReportType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private MemberResponse from_id;
    private MemberResponse to_id;
    private String content;
    @CreationTimestamp
    private LocalDateTime reportTime;
    private ReportType type;

    public static ReportResponse of(Report report) {
        return ReportResponse.builder().from_id(MemberResponse.of(report.getFrom_id()))
                .to_id(MemberResponse.of(report.getTo_id()))
                .content(report.getContent())
                .reportTime(report.getReportTime())
                .type(report.getType())
                .build();
    }
}
