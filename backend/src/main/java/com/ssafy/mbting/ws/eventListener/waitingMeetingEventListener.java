package com.ssafy.mbting.ws.eventListener;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.mbting.api.service.OpenviduService;
import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueueSizeEnoughEvent;
import com.ssafy.mbting.ws.model.event.WaitingMeetingUserQueuedEvent;
import com.ssafy.mbting.ws.model.res.TestResponse;
import com.ssafy.mbting.ws.model.stompMessageBody.TempSuccess;
import com.ssafy.mbting.ws.model.vo.MeetingUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequiredArgsConstructor
public class waitingMeetingEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final OpenviduService openviduService;
    @EventListener
    public void onQueued(WaitingMeetingUserQueuedEvent event) {
        logger.debug("\n\n온큐드 핸들러 왔니?\n");
        logger.debug(event.toString());
        logger.debug("\n\n---email--\n\n"+event.getEmail());
        String jsonString="";
        //session name 필요
        simpMessagingTemplate.convertAndSend(event.getDestination() ,TestResponse.builder().mbti("ESTP").gender(Gender.MALE).command("proposal").build());
    }

    @EventListener
    public void onEnough(WaitingMeetingUserQueueSizeEnoughEvent event) {
    }

    @EventListener
    public void onMatched(WaitingMeetingUserMatchedEvent event) {
    }
}
