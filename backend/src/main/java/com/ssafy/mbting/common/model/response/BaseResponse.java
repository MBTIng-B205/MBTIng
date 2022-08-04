package com.ssafy.mbting.common.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponse {
    @Builder.Default
    Boolean success = null;
    @Builder.Default
    String message = null;
    @Builder.Default
    Object body = null;

}
