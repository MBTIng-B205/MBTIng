package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
