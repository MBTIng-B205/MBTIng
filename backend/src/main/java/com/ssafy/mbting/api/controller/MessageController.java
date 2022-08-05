package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.MessageDeleteRequest;
import com.ssafy.mbting.api.request.MessageReadRequest;
import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.api.response.MessageListResponse;
import com.ssafy.mbting.api.response.MessageResponse;
import com.ssafy.mbting.api.service.FriendService;
import com.ssafy.mbting.api.service.MessageService;
import com.ssafy.mbting.common.util.BaseResponseUtil;
import com.ssafy.mbting.common.util.PageNavigation;
import com.ssafy.mbting.db.entity.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final FriendService friendService;
    private final BaseResponseUtil baseResponseUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //하나만 보는거는 message id 가 맞는거 같음
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable("messageId") Long messageId) {
        Message message = messageService.getMessage(messageId);
        message =messageService.readMessage(messageId,true);
        if(friendService.checkFriend(message.getFromId(),message.getToId())&&friendService.checkFriend(message.getToId(),message.getFromId())){
            message.setFriendflag(true);
        }
        return baseResponseUtil.success(MessageResponse.of(message,message.getToId(),message.getFromId()));
    }


    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendRequest messageSendRequest) {
        Message message = messageService.sendMessage(messageSendRequest);
        return baseResponseUtil.success(MessageResponse.of(message,message.getToId(),message.getFromId()));
    }

    //리턴 협의 필요
    @PutMapping("/read")
    public ResponseEntity<?> readMessage(@RequestBody  MessageReadRequest messageReadRequest) {
        for (long messageId : messageReadRequest.getReadList()) {
            Message message = messageService.readMessage(messageId, true);
        }
        return baseResponseUtil.success();
    }
    //리턴 협의 필요
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(@RequestBody  MessageDeleteRequest messageDeleteRequest) {
        for (long messageId : messageDeleteRequest.getDeletelist()) {
            try {
                if (messageDeleteRequest.getDeletedBy() == MessageDeleteRequest.DeletedBy.RECEIVER) {
                    Message message = messageService.deleteMessageTo(messageId);
                }
                if (messageDeleteRequest.getDeletedBy() == MessageDeleteRequest.DeletedBy.SENDER) {
                    Message message = messageService.deleteMessageFrom(messageId);
                }
            }
            catch (Exception e){
                return baseResponseUtil.fail("delete 실패");
            }
        }
        return baseResponseUtil.success();
    }
    //보낸 쪽지함
    @PostMapping("fromlist/{email}")
    public ResponseEntity<?> getAllMessagesFromMember(@PathVariable("email") String email,@RequestBody PageNavigation pageNavigation) {
        Page<Message> messages = messageService.getMessagesFromMember(email, pageNavigation);
        logger.debug("messages.getPageable() "+ messages.getPageable());
        List<MessageResponse> ml = new ArrayList<>();
        for(Message tmp : messages.getContent() ){

            ml.add(MessageResponse.of(tmp,tmp.getToId(),tmp.getFromId()));
        }
        return baseResponseUtil.success(MessageListResponse.builder().messages(ml).pageable((PageRequest) messages.getPageable()).build());
    }


    //받은 쪽지함
    @PostMapping("tolist/{email}")
    public ResponseEntity<?> getAllMessagesToMember(@PathVariable("email") String email,@RequestBody PageNavigation pageNavigation) {
        Page<Message> messages = messageService.getMessagesToMember(email, pageNavigation);
        logger.debug("messages.getPageable() "+ messages.getPageable());
        List<MessageResponse> ml = new ArrayList<>();
        for(Message tmp : messages.getContent() ){
            ml.add(MessageResponse.of(tmp,tmp.getToId(),tmp.getFromId()));
        }
        return baseResponseUtil.success(MessageListResponse.builder().messages(ml).pageable((PageRequest) messages.getPageable()).build());}
}
