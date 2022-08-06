package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("friendService")
@RequiredArgsConstructor
@Transactional
public class FriendServiceImpl implements FriendService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FriendRepository friendRepository;

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
