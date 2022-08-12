package com.ssafy.mbting.api.request;

import com.ssafy.mbting.db.enums.Gender;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequest {

	private String email;
	private String nickname;
	private Gender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	private String sido;
	private String mbti;
	private String profileUrl;
	@Builder.Default
	private List<String> interests = new ArrayList<>();
}
