package com.ssafy.mbting.api.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {

    @Builder.Default
    private List<MemberResponse> friends = new ArrayList<>();

    public static FriendResponse of(List<MemberResponse> friends) {
        return FriendResponse.builder()
                .friends(friends)
                .build();

    }
}
