package com.ssafy.mbting.api.request;

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
public class ReportRegisterRequest {

    private Member from_id;
    private Member to_id;
    private String content;
    @CreationTimestamp
    private LocalDateTime reportTime;
    private ReportType type;
}
