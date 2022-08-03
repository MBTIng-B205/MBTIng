package com.ssafy.mbting.common.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponse {
    Boolean success = null;
    String message = null;
    Object body = null;

}
