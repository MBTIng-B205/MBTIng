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
import com.ssafy.mbting.common.util.PagingResponse;
import com.ssafy.mbting.db.entity.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageService messageService;
    private final FriendService friendService;
    private final BaseResponseUtil baseResponseUtil;



    @GetMapping("/{messageId}/{readtype}")
    public ResponseEntity<?> getMessage(@PathVariable("messageId") Long messageId, @PathVariable("readtype") String readtype) {
        Message message = messageService.getMessage(messageId);

        if(readtype.equals("to")){
            message = messageService.readMessage(messageId, true);
            if(friendService.checkFriend(message.getToId(), message.getFromId())) {
                message.setTofriendflag(true);
            }
            else
                message.setTofriendflag(false);
        }
        else if(readtype.equals("from")){
            if(friendService.checkFriend(message.getFromId(), message.getToId()))
                message.setFromfriendflag(true);
            else
                message.setFromfriendflag(false);
        }
        else{
            return baseResponseUtil.fail("readtype??? ????????? ???????????????");
        }
        return baseResponseUtil.success(MessageResponse.of(message, message.getToId(), message.getFromId()));
    }

    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendRequest messageSendRequest) {
        Message message = messageService.sendMessage(messageSendRequest);
        return baseResponseUtil.success(MessageResponse.of(message, message.getToId(), message.getFromId()));
    }

    @PutMapping("/read")
    public ResponseEntity<?> readMessage(@RequestBody MessageReadRequest messageReadRequest) {
        for (long messageId : messageReadRequest.getReadList()) {
            Message message = messageService.readMessage(messageId, true);
        }
        return baseResponseUtil.success();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(@RequestBody MessageDeleteRequest messageDeleteRequest) {
        for (long messageId : messageDeleteRequest.getDeletelist()) {
            try {
                if (messageDeleteRequest.getDeletedBy() == MessageDeleteRequest.DeletedBy.RECEIVER) {
                    Message message = messageService.deleteMessageTo(messageId);
                }
                if (messageDeleteRequest.getDeletedBy() == MessageDeleteRequest.DeletedBy.SENDER) {
                    Message message = messageService.deleteMessageFrom(messageId);
                }
            } catch (Exception e) {
                return baseResponseUtil.fail("delete ??????");
            }
        }
        return baseResponseUtil.success();
    }

    @PostMapping("fromlist/{email}")
    public ResponseEntity<?> getAllMessagesFromMember(@PathVariable("email") String email, @RequestBody PageNavigation pageNavigation) {
        Page<Message> messages = messageService.getMessagesFromMember(email, pageNavigation);
        logger.debug("messages.getPageable() " + messages.getPageable());
        logger.debug("messages.getTotalElements() " + messages.getTotalElements());
        logger.debug("messages.getTotalPages() " + messages.getTotalPages());
        List<MessageResponse> ml = new ArrayList<>();
        PagingResponse pagingResponse = new PagingResponse();
        pagingResponse.setPageable(messages.getPageable());
        pagingResponse.setTotalcount(messages.getTotalElements());
        pagingResponse.setTotalpage(messages.getTotalPages());

        for (Message tmp : messages.getContent()) {
            ml.add(MessageResponse.of(tmp, tmp.getToId(), tmp.getFromId()));
        }

        return baseResponseUtil.success(MessageListResponse.builder()
                .messages(ml)
                .pagingResponse(pagingResponse)
                .build());
    }


    @PostMapping("tolist/{email}")
    public ResponseEntity<?> getAllMessagesToMember(@PathVariable("email") String email, @RequestBody PageNavigation pageNavigation) {
        Page<Message> messages = messageService.getMessagesToMember(email, pageNavigation);
        logger.debug("messages.getPageable() " + messages.getPageable());
        List<MessageResponse> ml = new ArrayList<>();
        PagingResponse pagingResponse = new PagingResponse();
        pagingResponse.setPageable(messages.getPageable());
        pagingResponse.setTotalcount(messages.getTotalElements());
        pagingResponse.setTotalpage(messages.getTotalPages());

        for (Message tmp : messages.getContent()) {
            ml.add(MessageResponse.of(tmp, tmp.getToId(), tmp.getFromId()));
        }

        return baseResponseUtil.success(MessageListResponse.builder()
                .messages(ml)
                .pagingResponse(pagingResponse)
                .build());
    }
}