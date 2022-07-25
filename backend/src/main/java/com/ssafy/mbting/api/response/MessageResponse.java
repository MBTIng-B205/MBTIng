package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.Message;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    String content;
    Long receiverId;
    Long senderId;
    String receiverNickName;
    String senderNickName;
    String receiverProfileUrl;
    String senderProfileUrl;
    boolean read;
    boolean deletedByReceiver;
    boolean deletedBySender;
    LocalDateTime sendTime;

    public static MessageResponse of(Message message) {
        return MessageResponse.builder()
                .content(message.getContent())
                .receiverId(message.getToId().getId())
                .senderId(message.getFromId().getId())
                .receiverNickName(message.getToId().getNickname())
                .senderNickName(message.getFromId().getNickname())
                .receiverProfileUrl(message.getToId().getProfileUrl())
                .senderProfileUrl(message.getFromId().getProfileUrl())
                .read(message.isReadByTo())
                .deletedByReceiver(message.isDeletedByTo())
                .deletedBySender(message.isDeletedByFrom())
                .sendTime(message.getSendTime())
                .build();
    }
}
