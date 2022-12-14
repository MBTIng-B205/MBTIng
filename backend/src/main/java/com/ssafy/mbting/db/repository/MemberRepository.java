package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    Member findByNickname(String nickname);
    Long countAllByNicknameAndDeleted(String nickname, boolean deleted);
}
