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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;


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

    @Override
    public Page<Message> getMessagesFromMember(String email, PageNavigation pageNavigation) {

        Sort sendsort = Sort.by("sendTime").descending();
        PageRequest pageRequest =PageRequest.of(pageNavigation.getPage(), pageNavigation.getSize(),sendsort);

        Page<Message> all;
        if(pageNavigation.getSearchUtil().getKey().equals("content")){
            all = messageRepository.findByFromIdAndContentContainingAndDeletedByFrom(memberRepository.findByEmail(email),pageNavigation.getSearchUtil().getWord(),false,pageRequest);
        }
        else if(pageNavigation.getSearchUtil().getKey().equals("nickname")){
            all = messageRepository.findByFromIdAndToIdAndDeletedByFrom(memberRepository.findByEmail(email),memberRepository.findByNickname(pageNavigation.getSearchUtil().getWord()),false,pageRequest);
        }
        else {
             all = messageRepository.findAllByFrom(memberRepository.findByEmail(email), pageRequest);
        }
        return all;
    }

    @Override
    public Page<Message> getMessagesToMember(String email, PageNavigation pageNavigation) {
        Sort sendsort = Sort.by("sendTime").descending();
        PageRequest pageRequest = PageRequest.of(pageNavigation.getPage(), pageNavigation.getSize(),sendsort);

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
