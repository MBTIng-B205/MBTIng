package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.request.MessageSendRequest;
import com.ssafy.mbting.common.util.PageNavigation;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Message;
import com.ssafy.mbting.db.repository.MemberRepository;
import com.ssafy.mbting.db.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${com.mbting.ddl_auto}")
    private String ddlAuto;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;


    @PostConstruct
    public void init() {
        if ("create".equalsIgnoreCase(ddlAuto)) {
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

            for (int i = 0; i < 100; i++) {
                Message msg = new Message();
                msg.setFromId(m1);
                msg.setToId(m2);
                msg.setContent("이것은 메시지 " + i + "번입니다.");
                messageRepository.save(msg);
            }

            for (int i = 0; i < 100; i++) {
                Message msg = new Message();
                msg.setToId(m1);
                msg.setFromId(m2);
                msg.setContent("이것은 " + i + "번입니다!!!!!");
                messageRepository.save(msg);
            }
        }
    }

    @Override
    public Message getMessage(Long messageId) {
        return messageRepository.findById(messageId).get();
    }

    @Override
    public Message sendMessage(MessageSendRequest messageSendRequest) {
        Member memberFrom = memberRepository.findByEmail(messageSendRequest.getSenderId());
        Member memberTo = memberRepository.findByEmail(messageSendRequest.getReceiverId());
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
    //보낸 쪽지함
    @Override
    public Page<Message> getMessagesFromMember(String email, PageNavigation pageNavigation) {
        PageRequest pageRequest = PageRequest.of(pageNavigation.getPage(), pageNavigation.getSize());
        Page<Message> all;
        if(pageNavigation.getSearchUtil().getKey().equals("content")){
            logger.debug("\n\n\n content \n\n\n");
            all = messageRepository.findByFromIdAndContentContainingAndDeletedByFrom(memberRepository.findByEmail(email),pageNavigation.getSearchUtil().getWord(),false,pageRequest);
        }
        else if(pageNavigation.getSearchUtil().getKey().equals("nickname")){
            logger.debug("\n\n\n nickname \n\n\n");
            all = messageRepository.findByFromIdAndToIdAndDeletedByFrom(memberRepository.findByEmail(email),memberRepository.findByNickname(pageNavigation.getSearchUtil().getWord()),false,pageRequest);
        }
        else {
            logger.debug("\n\n\n no keyword \n\n\n");
             all = messageRepository.findAllByFrom(memberRepository.findByEmail(email), pageRequest);
        }
        return all;
    }
    //받은 쪽지함
    @Override
    public Page<Message> getMessagesToMember(String email, PageNavigation pageNavigation) {
        PageRequest pageRequest = PageRequest.of(pageNavigation.getPage(), pageNavigation.getSize());
        Page<Message> all;
        if(pageNavigation.getSearchUtil().getKey().equals("content")){
            all = messageRepository.findByToIdAndContentContainingAndDeletedByTo(memberRepository.findByEmail(email),pageNavigation.getSearchUtil().getWord(),false,pageRequest);
        }
        else if(pageNavigation.getSearchUtil().getKey().equals("nickname")){
            all = messageRepository.findByToIdAndFromIdAndDeletedByTo(memberRepository.findByEmail(email),memberRepository.findByNickname(pageNavigation.getSearchUtil().getWord()),false,pageRequest);
        }
        else {
            all = messageRepository.findAllByTo(memberRepository.findByEmail(email), pageRequest);
        }
        return all;
    }
}
