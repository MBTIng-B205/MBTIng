package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
