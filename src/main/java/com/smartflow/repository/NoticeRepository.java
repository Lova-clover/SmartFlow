package com.smartflow.repository;

import com.smartflow.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByCreatedAtDesc();
    List<Notice> findTop5ByOrderByCreatedAtDesc();
}
