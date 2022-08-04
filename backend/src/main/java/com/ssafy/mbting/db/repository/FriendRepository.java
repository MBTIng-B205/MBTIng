package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Friend;
import com.ssafy.mbting.db.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByFromId(Member member);
}
