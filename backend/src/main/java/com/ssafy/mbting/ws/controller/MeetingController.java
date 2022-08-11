package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.res.TestResponse;
import com.ssafy.mbting.ws.model.stompMessageBody.msg.RequestProposal;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MeetingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OpenviduService openviduService ;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/indi/{email}")
    @SendTo("/ws/sub/indi/{email}")
    public void TestMessage(@DestinationVariable("email") String email ,@Payload Message<RequestProposal> proMessage) {
        logger.debug("\n\nproMessage: {}\n",proMessage);
        logger.debug("\n\npayload: {}\n", proMessage.getPayload());
        logger.debug("\n\ncommand: {}\n", proMessage.getPayload().getCommand());
//        System.out.println("\n\n\n\n\n\n\n 들어옴 !!!!!!!!!!!"+result.toString());
        String token =openviduService.getToken("abc");
        simpMessagingTemplate.convertAndSend("/ws/sub/indi/"+email, TestResponse.builder().gender(Gender.MALE).command("accept").mbti("ESTP").token(token).build());
    }
}
