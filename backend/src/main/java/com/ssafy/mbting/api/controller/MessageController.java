package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.MessageDeleteRequest;
import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.api.response.MessageListResponse;
import com.ssafy.mbting.api.response.MessageResponse;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageService messageService;
    private final BaseResponseUtil baseResponseUtil;

    //하나만 보는거는 message id 가 맞는거 같음
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable("messageId") Long messageId) {
        Message message = messageService.getMessage(messageId);
        message =messageService.readMessage(messageId,true);
        return baseResponseUtil.success(MessageResponse.of(message,message.getFromId(),message.getToId()));
    }

    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendRequest messageSendRequest) {
        Message message = messageService.sendMessage(messageSendRequest);
        return baseResponseUtil.success(MessageResponse.of(message,message.getFromId(),message.getToId()));
    }

    //리턴 협의 필요
    @PutMapping("/read")
    public ResponseEntity<?> readMessage(@RequestParam(value="readlist[]") List<Long> readlist) {
        for (long messageId : readlist) {
            Message message = messageService.readMessage(messageId, true);
        }
        return baseResponseUtil.success();
    }

    //리턴 협의 필요
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(@RequestParam(value="deletelist[]") List<Long> deletelist,MessageDeleteRequest messageDeleteRequest) {
        for (long messageId : deletelist) {
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
        return baseResponseUtil.success(MessageListResponse.builder()
                .messages(ml)
                .pageable((PageRequest) messages.getPageable())
                .build());
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
        return baseResponseUtil.success(MessageListResponse.builder()
                .messages(ml)
                .pageable((PageRequest) messages.getPageable())
                .build());}
}
