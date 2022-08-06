package com.ssafy.mbting.api.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageReadRequest {

    private Boolean read;
}
