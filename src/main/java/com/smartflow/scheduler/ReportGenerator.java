package com.smartflow.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportGenerator {

    // 매주 월요일 오전 9시에 근태/결재 리포트 생성 (예시)
    @Scheduled(cron = "0 0 9 ? * MON")
    public void generateWeeklyReport() {
        log.info("주간 근태 및 결재 통계 리포트 생성 시작...");
        // TODO: 실제 통계 로직 구현
    }
}
