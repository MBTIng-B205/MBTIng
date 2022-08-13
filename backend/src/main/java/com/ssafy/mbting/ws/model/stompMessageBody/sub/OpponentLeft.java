package com.ssafy.mbting.ws.model.stompMessageBody.sub;

import com.ssafy.mbting.ws.model.vo.StompUserStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpponentLeft {

    private StompUserStatus status;
}
