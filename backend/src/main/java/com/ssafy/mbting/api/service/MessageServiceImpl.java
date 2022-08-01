package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Message;
import com.ssafy.mbting.db.repository.MemberRepository;
import com.ssafy.mbting.db.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;


    @PostConstruct
    public void init() {
        Member m1 = new Member();
        m1.setNickname("홍길동");
        m1.setGender(true);
        Member m2 = new Member();
        m2.setNickname("유관순");
        m2.setGender(false);


        m1.setEmail("rlwls1101@hamail.net");
        m2.setEmail("rlwl202@gmail.com");
        logger.info("\n\n홍길동 : {}\n", memberRepository.save(m1).getId());
        logger.info("\n\n유관순 : {}\n", memberRepository.save(m2).getId());
    }

    @Override
    public Message getMessage(Long messageId) {
        return messageRepository.findById(messageId).get();
    }

    @Override
    public Message sendMessage(MessageSendRequest messageSendRequest) {
        Member memberFrom = memberRepository.findById(messageSendRequest.getSenderId()).get();
        Member memberTo = memberRepository.findById(messageSendRequest.getReceiverId()).get();
        Message message = new Message();
        message.setFromId(memberFrom);
        message.setToId(memberTo);
        message.setContent(messageSendRequest.getContent());
        return messageRepository.save(message);
    }

    @Override
    public Message readMessage(Long messageId, boolean read) {
        Message message = messageRepository.findById(messageId).get();
        message.setReadByTo(read);
        return message;
    }

    @Override
    public Message deleteMessageFrom(Long messageId) {
        Message message = messageRepository.findById(messageId).get();
        message.setDeletedByFrom(true);
        return message;
    }

    @Override
    public Message deleteMessageTo(Long messageId) {
        Message message = messageRepository.findById(messageId).get();
        message.setDeletedByTo(true);
        return message;
    }
}
