package com.ssafy.mbting.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    Boolean success = null;
    String message = null;
    Object body = null;

}
