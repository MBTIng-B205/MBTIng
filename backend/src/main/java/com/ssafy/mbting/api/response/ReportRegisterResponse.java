package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.ReportType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRegisterResponse {
    private ReportResponse report;
}
