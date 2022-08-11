package com.ssafy.mbting.ws.model.stompMessageBody.sub;

import com.ssafy.mbting.api.response.MemberResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoom {

    private MemberResponse opponent;
    private String openviduToken;
}
