package com.ssafy.mbting.ws.model.stompMessageBody;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseMessageBody {
    private String command;
    private Object data;
}
