package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.common.searchSpec.FriendSearchSpec;
import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("friendService")
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{
    private static FriendRepository friendRepository;
    @Override
    public List<MemberResponse> getFriendList(Member member) {
        List<Friend> friends = member.getFriends();
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
        friendRepository.delete(Friend.of(fromMember, toMember));
    }

    @Override
    public List<MemberResponse> getFriendByNickname(Member member, String nickname) {
        List<Friend> friends = member.getFriends();
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
        List<Friend> friends = member.getFriends();
        List<MemberResponse> myFriends = new ArrayList<>();
        for (Friend friend : friends) {
            Member toId = friend.getToId();
            if(mbti.equals(toId.getMbti()))
                myFriends.add(MemberResponse.of(toId));
        }
        return myFriends;
    }
}
