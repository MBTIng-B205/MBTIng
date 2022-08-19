package com.ssafy.mbting.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse {
    private Boolean visited;
    private String jwt;
    private MemberResponse member;
}
