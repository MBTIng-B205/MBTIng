package com.ssafy.mbting.db.initDataLoader;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Message;
import com.ssafy.mbting.db.repository.MemberRepository;
import com.ssafy.mbting.db.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageLoader {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private @Value("${com.mbting.ddl_auto}") String ddl_auto;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    @PostConstruct
    private void init() {
        if ("create".equalsIgnoreCase(ddl_auto)) {
            Member hong = memberRepository.save(Member.builder()
                    .nickname("홍길동")
                    .email("gildong@hong.com")
                    .gender(true)
                    .build());
            Member you = memberRepository.save(Member.builder()
                    .nickname("유관순")
                    .email("gwansun@you.com")
                    .gender(false)
                    .build());

            logger.info("\n\n홍길동 id : {}\n", hong.getId());
            logger.info("\n\n유관순 id : {}\n", you.getId());

            List<Message> messages = new ArrayList<>();
            for (int i = 1; i <= 71; i++) {
                messages.add(Message.builder()
                        .fromId(hong)
                        .toId(you)
                        .content("이것은 홍길동이 유관순에게 보내는 메시지 #" + i)
                        .build());
            }
            for (int i = 1; i <= 71; i++) {
                messages.add(Message.builder()
                        .fromId(you)
                        .toId(hong)
                        .content("이것은 유관순이 홍길동에게 보내는 메시지 #" + i)
                        .build());
            }
            messageRepository.saveAll(messages);

            logger.info("\n\n메시지 {}개가 성공적으로 들어감\n", messages.size());
        }
    }
}
