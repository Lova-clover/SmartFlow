package com.smartflow.service;

import com.smartflow.domain.AttendanceRecord;
import com.smartflow.dto.AttendanceRecordDto;
import com.smartflow.repository.AttendanceRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRecordRepository attendanceRecordRepository;

    // 출근 기록 등록 or 갱신
    public AttendanceRecordDto clockIn(String username) {
        LocalDate today = LocalDate.now();

        Optional<AttendanceRecord> optional = attendanceRecordRepository.findByUsernameAndWorkDate(username, today);
        AttendanceRecord record = optional.orElse(
                AttendanceRecord.builder()
                        .username(username)
                        .workDate(today)
                        .status("출근")
                        .build()
        );

        if (record.getClockInTime() == null) {
            record.setClockInTime(LocalDateTime.now());
            record.setStatus("출근");
        } else {
            throw new RuntimeException("이미 출근 기록이 있습니다.");
        }

        attendanceRecordRepository.save(record);

        return toDto(record);
    }

    // 퇴근 기록 등록 or 갱신
    public AttendanceRecordDto clockOut(String username) {
        LocalDate today = LocalDate.now();

        AttendanceRecord record = attendanceRecordRepository.findByUsernameAndWorkDate(username, today)
                .orElseThrow(() -> new RuntimeException("출근 기록이 없습니다."));

        if (record.getClockOutTime() == null) {
            record.setClockOutTime(LocalDateTime.now());

            // 근무 시간 계산
            Duration duration = Duration.between(record.getClockInTime(), record.getClockOutTime());
            record.setWorkDurationMinutes((int) duration.toMinutes());

            record.setStatus("퇴근 완료");
        } else {
            throw new RuntimeException("이미 퇴근 기록이 있습니다.");
        }

        attendanceRecordRepository.save(record);

        return toDto(record);
    }

    private AttendanceRecordDto toDto(AttendanceRecord record) {
        return AttendanceRecordDto.builder()
                .id(record.getId())
                .username(record.getUsername())
                .workDate(record.getWorkDate())
                .clockInTime(record.getClockInTime())
                .clockOutTime(record.getClockOutTime())
                .workDurationMinutes(record.getWorkDurationMinutes())
                .status(record.getStatus())
                .build();
    }

    public Optional<AttendanceRecordDto> getTodayRecord(String username) {
        return attendanceRecordRepository.findByUsernameAndWorkDate(username, LocalDate.now())
                .map(this::toDto);
    }

    public List<AttendanceRecordDto> findAll() {
        return attendanceRecordRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceRecordDto> findAllRecords() {
        List<AttendanceRecord> records = attendanceRecordRepository.findAll();
        return records.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
