package com.ssafy.mbting.api.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendRequest {
    private Long senderId;
    private Long receiverId;
    private String content;
}
