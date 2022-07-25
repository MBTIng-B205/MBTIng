package com.ssafy.mbting.api.controller;

import com.ssafy.mbting.api.request.MessageDeleteRequest;
import com.ssafy.mbting.api.request.MessageReadRequest;
import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.api.response.MessageResponse;
import com.ssafy.mbting.api.service.MessageService;
import com.ssafy.mbting.db.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable("messageId") Long messageId) {
        Message message = messageService.getMessage(messageId);
        return ResponseEntity.ok().body(MessageResponse.of(message));
    }

    @PostMapping("/")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendRequest messageSendRequest) {
        Long id = messageService.sendMessage(messageSendRequest).getId();
        return ResponseEntity.created(URI.create("/api/message/" + id)).build();
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<?> readMessage(@PathVariable("messageId") Long messageId
            , @RequestBody MessageReadRequest messageReadRequest) {
        Message message = messageService.readMessage(messageId, messageReadRequest.isRead());
        return ResponseEntity.accepted().body(MessageResponse.of(message));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable("messageId") Long messageId
            , @RequestBody MessageDeleteRequest messageDeleteRequest) {

        if (messageDeleteRequest.getDeletedBy() == MessageDeleteRequest.DeletedBy.RECEIVER) {
            Message message = messageService.deleteMessageTo(messageId);
            return ResponseEntity.accepted().body(MessageResponse.of(message));
        }

        else if (messageDeleteRequest.getDeletedBy() == MessageDeleteRequest.DeletedBy.SENDER) {
            Message message = messageService.deleteMessageFrom(messageId);
            return ResponseEntity.accepted().body(MessageResponse.of(message));
        }

        return ResponseEntity.badRequest().build();
    }
}
