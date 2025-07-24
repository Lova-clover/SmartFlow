package com.smartflow.service;

import com.smartflow.dto.CreateApprovalRequest;
import com.smartflow.dto.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentService {

    void createDocument(CreateApprovalRequest request, String username);
    void approveDocument(Long id, String approverUsername);
    void rejectDocument(Long id, String rejecterUsername);

    List<Document> getDocumentsByUser(String username);
    Page<Document> getPagedDocumentsByUser(String username, String keyword, boolean includeShared, int page);

    Document getDocumentById(Long id);
}
