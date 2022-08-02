package com.ssafy.mbting.common.model.response;

import lombok.Builder;

@Builder
public class BaseResponse {
    Boolean success = null;
    String message = null;
    Object body = null;

}
