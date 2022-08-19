package com.ssafy.mbting.ws.eventListener;

import com.ssafy.mbting.ws.model.event.OrphanOccurEvent;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.BaseMessageBody;
import com.ssafy.mbting.ws.model.stompMessageBody.sub.OpponentLeft;
import com.ssafy.mbting.ws.model.vo.IndividualDestination;
import com.ssafy.mbting.ws.model.vo.StompUser;
import com.ssafy.mbting.ws.model.vo.StompUserStatus;
import com.ssafy.mbting.ws.service.AppStompService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class OrphanOccurEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AppStompService appStompService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Async
    @EventListener
    public void onOrphanOccur(OrphanOccurEvent event) {
        String orphanSessionId = event.getOrphanSessionId();

        logger.debug("\n\n!고아 발생! 이벤트 발생\n고아 세션 ID: {}\n", orphanSessionId);

        appStompService.getStompUserBySessionId(orphanSessionId).ifPresent(user -> {
            simpMessagingTemplate.convertAndSend(
                    IndividualDestination.of(user.getEmail()).toString(),
                    BaseMessageBody.builder()
                            .command("opponentLeft")
                            .data(OpponentLeft.builder().status(user.getStompUserStatus()).build())
                            .build());
        });
    }
}
