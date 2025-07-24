package com.smartflow.service;

import com.smartflow.domain.ApprovalDocument;
import com.smartflow.dto.CreateApprovalRequest;
import com.smartflow.dto.Document;
import com.smartflow.repository.ApprovalDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final ApprovalDocumentRepository repo;

    @Override
    public void createDocument(CreateApprovalRequest req, String username) {
        ApprovalDocument d = new ApprovalDocument();
        d.setTitle(req.getTitle());
        d.setContent(req.getContent());
        d.setStatus("PENDING");
        d.setAuthorUsername(username);
        d.setShared(req.isShared());
        repo.save(d);
    }

    @Override
    public void approveDocument(Long id, String approverUsername) {
        repo.findById(id).ifPresent(d -> {
            d.setStatus("APPROVED");
            d.setApprovedBy(approverUsername);
            repo.save(d);
        });
    }

    @Override
    public void rejectDocument(Long id, String rejecterUsername) {
        repo.findById(id).ifPresent(d -> {
            d.setStatus("REJECTED");
            d.setRejectedBy(rejecterUsername);
            repo.save(d);
        });
    }

    @Override
    public List<Document> getDocumentsByUser(String username) {
        return repo.findVisibleDocs(username).stream()
                .map(d -> new Document(
                        d.getId(),
                        d.getTitle(),
                        d.getAuthorUsername(),
                        d.getStatus(),
                        d.isShared(),
                        d.getContent()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public Page<Document> getPagedDocumentsByUser(String username, String keyword, boolean includeShared, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());

        boolean hasKeyword = keyword != null && !keyword.isBlank();

        Page<ApprovalDocument> result;

        if (includeShared && hasKeyword) {
            result = repo.findWithSharedAndKeyword(username, keyword, pageable);
        } else if (!includeShared && hasKeyword) {
            result = repo.findWithoutSharedAndKeyword(username, keyword, pageable);
        } else if (includeShared) {
            result = repo.findWithShared(username, pageable);
        } else {
            result = repo.findWithoutShared(username, pageable);
        }

        return result.map(d -> new Document(
                d.getId(),
                d.getTitle(),
                d.getAuthorUsername(),
                d.getStatus(),
                d.isShared(),
                d.getContent()
        ));
    }
    @Override
    public Document getDocumentById(Long id) {
        return repo.findById(id)
                .map(d -> new Document(
                        d.getId(),
                        d.getTitle(),
                        d.getAuthorUsername(),
                        d.getStatus(),
                        d.isShared(),
                        d.getContent()))
                .orElse(null);
    }
}