package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.common.util.PageNavigation;
import com.ssafy.mbting.db.entity.Message;
import org.springframework.data.domain.Page;

public interface MessageService {
    Message getMessage(Long messageId);
    Message sendMessage(MessageSendRequest messageSendRequest);
    Message readMessage(Long messageId, boolean read);
    Message deleteMessageFrom(Long messageId);
    Message deleteMessageTo(Long messageId);
    Page<Message> getMessagesFromMember(String email, PageNavigation pageNavigation);
    Page<Message> getMessagesToMember(String member, PageNavigation pageRequest);
}
