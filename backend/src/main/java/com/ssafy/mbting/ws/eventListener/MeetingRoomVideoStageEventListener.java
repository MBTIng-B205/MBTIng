package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.room.AddFriendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingRoomVideoStageEventListener {

    @Async
    @EventListener
    public void onAddFriend(AddFriendEvent event) {

    }
}
