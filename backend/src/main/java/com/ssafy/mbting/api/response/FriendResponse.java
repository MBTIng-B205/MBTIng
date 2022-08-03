package com.ssafy.mbting.api.response;

import com.ssafy.mbting.db.entity.Member;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {
    private List<MemberResponse> friends;

    public static FriendResponse of(List<MemberResponse> friends) {
        return FriendResponse.builder()
                .friends(friends)
                .build();

    }
}
