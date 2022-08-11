package com.ssafy.mbting.ws.model.stompMessageBody.msg;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestProposal {
    String command;
    String data;
}
