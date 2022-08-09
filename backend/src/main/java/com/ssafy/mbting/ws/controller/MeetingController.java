package com.ssafy.mbting.ws.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MeetingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/indi/{email}")
    @SendTo("/ws/sub/indi/{email}")
    public String testMethod(@DestinationVariable("email") String email) {
        logger.debug("\n\n소켓 컨트롤러로 오니???\n");
        return "success";
    }
}
