package com.smartflow.repository;

import com.smartflow.domain.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUsernameOrderByStartDateDesc(String username);
}
