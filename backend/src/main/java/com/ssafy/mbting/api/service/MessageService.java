package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.db.entity.Message;

public interface MessageService {
    Message getMessage(Long messageId);
    Message sendMessage(MessageSendRequest messageSendRequest);
    Message readMessage(Long messageId, boolean read);
    Message deleteMessageFrom(Long messageId);
    Message deleteMessageTo(Long messageId);
}
