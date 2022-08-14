package com.ssafy.mbting.ws.model.stompMessageBody.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendBody {

    private String fromEmail;
    private String toEmail;
    private Boolean addOrRemove;
}
