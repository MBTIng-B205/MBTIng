package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Message;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    Long id;
    String content;
    //member 리스폰즈를 건드림
    MemberResponse receiver;
    MemberResponse sender;
    Boolean read;
    Boolean deletedByReceiver;
    Boolean deletedBySender;
    Boolean friendflag;
    LocalDateTime sendTime;

    public static MessageResponse of(Message message, Member receiver, Member sender) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .receiver(MemberResponse.of(receiver))
                .sender(MemberResponse.of(sender))
                .read(message.getReadByTo())
                .deletedByReceiver(message.getDeletedByTo())
                .deletedBySender(message.getDeletedByFrom())
                .sendTime(message.getSendTime())
                .friendflag(message.getFriendflag())
                .build();
    }
}
