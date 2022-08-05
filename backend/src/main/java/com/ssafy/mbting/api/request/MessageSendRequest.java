package com.ssafy.mbting.api.request;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendRequest {
    private String senderId;
    private String receiverId;
    private String content;
    @CreationTimestamp
    private LocalDateTime sendTime;
}
