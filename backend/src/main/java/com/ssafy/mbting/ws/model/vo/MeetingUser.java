package com.ssafy.mbting.ws.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingUser implements Comparable<MeetingUser> {

    private String email;
    private LocalDateTime enterTime;
    private String sido;
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    @Override
    public int compareTo(MeetingUser o) {
        return this.getEnterTime().compareTo(o.getEnterTime());
    }
}
