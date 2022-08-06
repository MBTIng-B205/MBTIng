package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Member;
import com.ssafy.mbting.db.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//searchutil key: nickname word :이기진
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m FROM Message m WHERE m.fromId = :fromId and m.deletedByFrom = false")
    Page<Message> findAllByFrom(@Param("fromId") Member memberFrom, Pageable pageable);
    @Query(value = "SELECT m FROM Message m WHERE m.toId = :toId and m.deletedByTo = false")
    Page<Message> findAllByTo(@Param("toId")  Member memberTo, Pageable pageable);
    Page<Message> findByFromIdAndContentContainingAndDeletedByFrom(Member fromId,String content , boolean deletedByFrom, Pageable pageable);
    Page<Message> findByFromIdAndToIdAndDeletedByFrom(Member fromId,Member toId, boolean deletedByFrom, Pageable pageable);
    Page<Message> findByToIdAndContentContainingAndDeletedByTo(Member toId,String content , boolean deletedByTo, Pageable pageable);
    Page<Message> findByToIdAndFromIdAndDeletedByTo(Member toId,Member fromId, boolean deletedByTo, Pageable pageable);
}
