package com.ssafy.mbting.ws.model.res;

import com.ssafy.mbting.db.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResponse {
    String command;
    String mbti;
    String token;
   private Gender gender;
}
