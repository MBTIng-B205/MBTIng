package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
