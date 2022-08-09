package com.ssafy.mbting.ws.model.vo;

import com.ssafy.mbting.db.enums.Gender;
import com.ssafy.mbting.ws.model.stompMessageHeader.SubscribeHeader;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingUser {

    private Gender gender;
    private String sido;
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public static MeetingUser of(SubscribeHeader subscribeHeader) {
        return MeetingUser.builder()
                .gender(Gender.valueOf(subscribeHeader.getGender().trim().toUpperCase()))
                .sido(subscribeHeader.getSido())
                .interests(subscribeHeader.getInterests())
                .build();
    }
}
