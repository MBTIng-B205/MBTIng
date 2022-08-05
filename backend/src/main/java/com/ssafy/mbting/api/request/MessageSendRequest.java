package com.ssafy.mbting.api.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendRequest {
    private String senderId;
    private String receiverId;
    private String content;
}
