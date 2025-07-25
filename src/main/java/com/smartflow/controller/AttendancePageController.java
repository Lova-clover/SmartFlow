package com.smartflow.controller;

import com.smartflow.dto.AttendanceRecordDto;
import com.smartflow.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendancePageController {

    private final AttendanceService attendanceService;

    @GetMapping
    public String attendancePage(Model model, @AuthenticationPrincipal(expression = "username") String username,
                                 @AuthenticationPrincipal(expression = "authorities") Collection<? extends GrantedAuthority> authorities) {
        // 개인 근태 페이지에 필요한 데이터 있으면 넣기
        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);
        return "attendance";
    }

    @GetMapping("/admin-list")
    public String adminAttendanceList(Model model) {
        List<AttendanceRecordDto> records = attendanceService.findAllRecords();
        model.addAttribute("records", records);
        return "admin-attendance-list";
    }
}