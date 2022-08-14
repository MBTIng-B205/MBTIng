package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.room.AddFriendEvent;
import com.ssafy.mbting.ws.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingRoomVideoStageEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MeetingRoomService meetingRoomService;

    @Async
    @EventListener
    public void onAddFriend(AddFriendEvent event) {
        String sessionId = event.getSessionId();

        logger.debug("\n\n친구 추가 이벤트 발생\nSession ID: {}\n", sessionId);

        meetingRoomService.setFriendResultToTrue(sessionId);
    }
}
