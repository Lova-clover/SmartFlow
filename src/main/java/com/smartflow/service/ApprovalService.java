package com.smartflow.service;

import com.smartflow.domain.ApprovalDocument;
import com.smartflow.domain.Status;
import com.smartflow.repository.ApprovalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalDocumentRepository approvalDocumentRepository;

    @Transactional
    public void updateStatus(Long id, Status status) {
        ApprovalDocument doc = approvalDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        doc.setStatus(String.valueOf(status));
    }
}
