package com.smartflow.controller;

import org.springframework.ui.Model;
import com.smartflow.dto.AttendanceRecordDto;
import com.smartflow.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn(@AuthenticationPrincipal(expression = "username") String username) {
        try {
            AttendanceRecordDto dto = attendanceService.clockIn(username);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/clock-out")
    public ResponseEntity<?> clockOut(@AuthenticationPrincipal(expression = "username") String username) {
        try {
            AttendanceRecordDto dto = attendanceService.clockOut(username);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayStatus(@AuthenticationPrincipal(expression = "username") String username) {
        return attendanceService.getTodayRecord(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(new AttendanceRecordDto()));
    }

    @GetMapping("/admin/all")
    public String viewAllAttendance(Model model) {
        List<AttendanceRecordDto> records = attendanceService.findAll();
        model.addAttribute("records", records);
        return "attendance/admin-attendance-list";
    }

}
