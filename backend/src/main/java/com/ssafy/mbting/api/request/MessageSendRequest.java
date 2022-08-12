package com.ssafy.mbting.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendRequest {
    private String senderId;
    private String receiverId;
    private String content;
    @CreationTimestamp
    private LocalDateTime sendTime;
}
