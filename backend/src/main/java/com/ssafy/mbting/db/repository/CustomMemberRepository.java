package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Member;

public interface CustomMemberRepository {
    Member findByEmail(String email);
}
