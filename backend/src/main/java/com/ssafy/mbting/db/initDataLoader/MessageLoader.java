package com.ssafy.mbting.db.initData;

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
public class MessageInitData {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private @Value("${com.mbting.ddl_auto}") String ddl_auto;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    @PostConstruct
    private void init() {
        if ("create".equalsIgnoreCase(ddl_auto)) {
            Member hong = Member.builder()
                    .nickname("홍길동")
                    .email("gildong@hong.com")
                    .gender(true)
                    .build();
            Member you = Member.builder()
                    .nickname("유관순")
                    .email("gwansun@you.com")
                    .gender(false)
                    .build();
            logger.info("\n\n홍길동 : {}\n", memberRepository.save(hong).getId());
            logger.info("\n\n유관순 : {}\n", memberRepository.save(you).getId());

            List<Message> messages = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                Message messagesFromHong;
                Message msg = new Message();
                msg.setFromId(null);
                msg.setToId(null);
                msg.setContent("이것은 메시지 " + i + "번입니다.");
                messageRepository.save(msg);
            }

            for (int i = 0; i < 100; i++) {
                Message msg = new Message();
                msg.setToId(null);
                msg.setFromId(null);
                msg.setContent("이것은 " + i + "번입니다!!!!!");
                messageRepository.save(msg);
            }
        }
    }
}
