package com.ssafy.mbting.api.service;

import com.ssafy.mbting.api.response.MemberResponse;
import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;

import java.util.List;

public interface FriendService {
    List<MemberResponse> getFriendList(Member member);

    Friend createFriend(Member fromMember, Member toMember);

    void deleteFriend(Member fromMember, Member toMember);
}
