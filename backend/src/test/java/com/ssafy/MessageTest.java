package com.ssafy;

import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.api.service.MessageService;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Message;
import com.ssafy.mbting.db.repository.MemberRepository;
import com.ssafy.mbting.db.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MessageTest {
    public Logger logger = LoggerFactory.getLogger(MessageTest.class);

    @Autowired
    MemberRepository mbr;

    @Autowired
    MessageRepository msgr;

    @Test
    void repositoryTest() {
        Member mb1 = new Member();
        Member mb2 = new Member();
        mb1.setNickname("홍길동");
        mb2.setNickname("유관순");


        mbr.save(mb1);
        mbr.save(mb2);

        Message msg1 = new Message();
        msg1.setContent("테스트 메시지입니다.");
        msg1.setFromId(mb1);
        msg1.setToId(mb2);

        msgr.save(msg1);
    }

    @Autowired
    MessageService msgs;

    @Test
    void sendTest() {
        Member mb1 = new Member();
        Member mb2 = new Member();
        mb1.setNickname("홍길동");
        mb2.setNickname("유관순");

        Long id1 = mbr.save(mb1).getId();
        Long id2 = mbr.save(mb2).getId();

        MessageSendRequest msgreq = new MessageSendRequest();
        msgreq.setContent("보내기 서비스 테스트");
        msgreq.setSenderId(id1);
        msgreq.setReceiverId(id2);

        msgs.sendMessage(msgreq);
    }

    @Test
    void readTest() {
        Member mb1 = new Member();
        Member mb2 = new Member();
        mb1.setNickname("홍길동");
        mb2.setNickname("유관순");

        mbr.save(mb1);
        mbr.save(mb2);

        Message msg1 = new Message();
        msg1.setContent("테스트 메시지입니다.");
        msg1.setFromId(mb1);
        msg1.setToId(mb2);

        Long msgid = msgr.save(msg1).getId();

        Message message = msgs.readMessage(msgid, true);
        logger.debug("isReadByTo: " + message.isReadByTo());
    }
}
