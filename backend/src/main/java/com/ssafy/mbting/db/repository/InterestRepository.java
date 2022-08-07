package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    
    Interest findByIname(String iname);
}
