package com.smartflow.repository;

import com.smartflow.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findAll();
    Optional<AttendanceRecord> findByUsernameAndWorkDate(String username, LocalDate date);
}
