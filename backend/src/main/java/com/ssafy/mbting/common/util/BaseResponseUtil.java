package com.ssafy.mbting.common.util;

import com.ssafy.mbting.common.model.response.BaseResponse;
import com.ssafy.mbting.common.model.response.BaseResponseBody;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseResponseUtil {

    public BaseResponseUtil(){}

    public ResponseEntity<?> success(){
        return ResponseEntity.ok().body(BaseResponse.builder().success(true).build());
    }

    public ResponseEntity<?> success(Object body) {
        return ResponseEntity.ok().body(BaseResponse.builder().success(true).body(body).build());
    }

    public ResponseEntity<?> fail(String message) {
        return ResponseEntity.ok().body(BaseResponse.builder().success(false).message(message).build());
    }
}
