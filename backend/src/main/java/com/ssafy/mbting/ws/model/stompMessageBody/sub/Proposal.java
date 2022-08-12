package com.ssafy.mbting.ws.model.stompMessageBody.sub;

import com.ssafy.mbting.ws.model.vo.MeetingUser;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {

    private String mbti;
    private String gender;
    private String sido;
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public static Proposal of(MeetingUser meetingUser) {
        return Proposal.builder()
                .mbti(meetingUser.getMbti())
                .gender(meetingUser.getGender().toString())
                .sido(meetingUser.getSido())
                .interests(meetingUser.getInterests())
                .build();
    }
}
