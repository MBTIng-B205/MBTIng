package com.ssafy.mbting.ws.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeetingRoom {
    private Boolean[] meetingRoomStatus;
    private String openviduSessionName;
    private String[] sessionIds;
}
