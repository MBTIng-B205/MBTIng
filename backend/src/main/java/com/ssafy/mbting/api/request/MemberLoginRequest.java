package com.ssafy.mbting.api.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {
	private String id;
	private String password;
}