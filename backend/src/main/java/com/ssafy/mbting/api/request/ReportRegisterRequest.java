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
    private String from_id;
    private String to_id;
    private String content;
    @CreationTimestamp
    private LocalDateTime reportTime;
}