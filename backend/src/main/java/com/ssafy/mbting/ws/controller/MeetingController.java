package com.ssafy.mbting.ws.controller;

import com.ssafy.mbting.ws.event.MeetingUserMatchedEvent;
import com.ssafy.mbting.ws.model.MeetingUser;
import com.ssafy.mbting.ws.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MeetingController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final MeetingService meetingService;

	@MessageMapping("/indi/{dest}")
	@SendTo("/ws/sub/indi/{dest}")
	public String testMethod(@DestinationVariable("dest") String dest,
							 @Header("email") String email,
//							 @Header("access-token") String accessToken,
							 @Header("gender") String gender,
							 @Header("interests") List<String> interests) {
		logger.info("\n\ndest: {}\nemail: {}\n",
				dest, email);

//		JWTVerifier verifier = JwtTokenUtil.getVerifier();
//		DecodedJWT decodedJWT = verifier.verify(accessToken);
//		String userEmail = decodedJWT.getSubject();
//
//		if (email == null || !email.equals(userEmail)) {
//			return "{\"success\": false}";
//		}

		meetingService.addUser(MeetingUser.builder()
				.email(email)
				.gender(MeetingUser.Gender.valueOf(gender))
				.interests(interests)
				.enterTime(LocalDateTime.now())
				.build());

		return "{\"success\": true}";
	}

	@EventListener
	public void onMatched(MeetingUserMatchedEvent event) {
		logger.info("\n\n여기가 마지막\n");
		simpMessagingTemplate.convertAndSend("/ws/sub/indi/" +
						event.getMeetingUser1().getEmail(),
				"Matched user: " + event.getMeetingUser2());
		simpMessagingTemplate.convertAndSend("/ws/sub/indi/" +
						event.getMeetingUser2().getEmail(),
				"Matched user: " + event.getMeetingUser1());
	}
}
