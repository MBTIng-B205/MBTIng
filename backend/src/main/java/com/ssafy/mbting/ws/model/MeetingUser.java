package com.ssafy.mbting.ws.model;

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

    public enum Gender {
        MALE, FEMALE
    }

    private String email;
    private LocalDateTime enterTime;
    private Gender gender;
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    public Gender getTargetGender() {
        if (gender == Gender.MALE)
            return Gender.FEMALE;
        return Gender.MALE;
    }

    @Override
    public int compareTo(MeetingUser o) {
        return this.getEnterTime().compareTo(o.getEnterTime());
    }
}
