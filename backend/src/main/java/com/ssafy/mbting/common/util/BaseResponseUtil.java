package com.ssafy.mbting.common.util;

import com.ssafy.mbting.common.model.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseResponseUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<?> success(){
        logger.debug("\n\n결과 바디가 없는 OK 응답이 나갈 것입니다.\n");
        return ResponseEntity.ok().body(BaseResponse.builder().success(true).build());
    }

    public ResponseEntity<?> success(Object body) {
        logger.debug("\n\n결과 바디가 있는 OK 응답이 나갈 것입니다.\n\n바디 : {}\n", body);
        return ResponseEntity.ok().body(BaseResponse.builder().success(true).body(body).build());
    }

    public ResponseEntity<?> fail(String message) {
        logger.debug("\n\n!!!실패 응답이 나갈 것입니다!!!\n\n메시지 : {}\n", message);
        return ResponseEntity.ok().body(BaseResponse.builder().success(false).message(message).build());
    }
}
