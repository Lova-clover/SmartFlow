package com.smartflow.service;

import com.smartflow.domain.Notice;
import com.smartflow.dto.NoticeDto;
import com.smartflow.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void create(NoticeDto dto, String username) {
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdBy(username)
                .createdAt(LocalDateTime.now())
                .build();
        noticeRepository.save(notice);
    }

    public List<NoticeDto> findAll() {
        return noticeRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(n -> NoticeDto.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .content(n.getContent())
                        .createdBy(n.getCreatedBy())
                        .createdAt(n.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public NoticeDto findById(Long id) {
        Notice n = noticeRepository.findById(id).orElseThrow();
        return NoticeDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .createdBy(n.getCreatedBy())
                .createdAt(n.getCreatedAt())
                .build();
    }

    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }

    public List<NoticeDto> findTop5() {
        return noticeRepository.findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(n -> NoticeDto.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .createdBy(n.getCreatedBy())
                        .createdAt(n.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public void update(Long id, NoticeDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항이 존재하지 않습니다."));
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        noticeRepository.save(notice);
    }
}
