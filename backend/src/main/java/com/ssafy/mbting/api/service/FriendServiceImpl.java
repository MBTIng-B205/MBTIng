package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.FriendRepository;
import com.ssafy.mbting.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("friendService")
@RequiredArgsConstructor
@Transactional
public class FriendServiceImpl implements FriendService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${com.mbting.ddl_auto}")
    private String ddlAuto;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        if ("create".equalsIgnoreCase(ddlAuto)) {
            for (int i = 0; i < 10; i++){
                Member m1 = new Member();
                m1.setNickname("손님" + i);
                m1.setEmail("test" + i + "@test.com");
                m1.setGender(true);
                m1.setMbti("ISFJ");
                memberRepository.save(m1);
                Member m2 = new Member();
                m2.setNickname("정훈" + i);
                m2.setEmail("hun" + i + "@test.com");
                m2.setGender(false);
                m2.setMbti("ENTP");
                memberRepository.save(m2);
                Friend friend = new Friend(m1, m2);
                friendRepository.save(friend);
            }
            for (int i = 0; i < 10; i++){
                String email = "test" + i + "@test.com";
                Member m1 = memberRepository.findByEmail(email);
                for (int j = i + 1; j < 10; j++){
                    String email2 = "hun" + j + "@test.com";
                    Member m2 = memberRepository.findByEmail(email2);
                    Friend friend = new Friend(m1, m2);
                    friendRepository.save(friend);
                }
            }
        }
    }

    @Override
    public List<MemberResponse> getFriendList(Member member) {
        List<Friend> friends = friendRepository.findAllByFromId(member);
        List<MemberResponse> myFriends = new ArrayList<>();
        for (Friend friend : friends) {
            Member toId = friend.getToId();
            myFriends.add(MemberResponse.of(toId));
        }
        return myFriends;
    }

    @Override
    public Friend createFriend(Member fromMember, Member toMember) {
        return friendRepository.save(Friend.of(fromMember, toMember));
    }

    @Override
    public void deleteFriend(Member fromMember, Member toMember) {
        Friend friend = friendRepository.findByFromIdAndToId(fromMember, toMember);
        fromMember.getFriends().remove(friend);
        friendRepository.delete(friend);
    }

    @Override
    public List<MemberResponse> getFriendByNickname(Member member, String nickname) {
        List<Friend> friends = friendRepository.findAllByFromId(member);
        List<MemberResponse> myFriends = new ArrayList<>();
        for (Friend friend : friends) {
            Member toId = friend.getToId();
            if(nickname.equals(toId.getNickname()))
                myFriends.add(MemberResponse.of(toId));
        }
        return myFriends;
    }

    @Override
    public List<MemberResponse> getFriendListByMbti(Member member, String mbti) {
        List<Friend> friends = friendRepository.findAllByFromId(member);
        List<MemberResponse> myFriends = new ArrayList<>();
        for (Friend friend : friends) {
            Member toId = friend.getToId();
            if(mbti.equals(toId.getMbti()))
                myFriends.add(MemberResponse.of(toId));
        }
        return myFriends;
    }
}
