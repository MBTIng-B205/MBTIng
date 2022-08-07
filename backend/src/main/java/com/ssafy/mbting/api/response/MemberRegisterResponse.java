package com.ssafy.mbting.api.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterResponse {

    private MemberResponse member;
}
