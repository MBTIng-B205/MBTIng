package com.ssafy.mbting.common.util;

import com.ssafy.mbting.common.model.response.BaseResponse;
import com.ssafy.mbting.common.model.response.BaseResponseBody;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseResponseUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BaseResponseUtil(){}

    public ResponseEntity<?> success(){
        return ResponseEntity.ok().body(BaseResponse.builder().success(true).build());
    }

    public ResponseEntity<?> success(Object body) {
        logger.debug("\n\n자 드가자~~~~~\n");
        return ResponseEntity.ok().body(BaseResponse.builder().success(true).body(body).build());
    }

    public ResponseEntity<?> fail(String message) {
        return ResponseEntity.ok().body(BaseResponse.builder().success(false).message(message).build());
    }
}
