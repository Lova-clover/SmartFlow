package com.smartflow.controller;

import com.smartflow.dto.CreateApprovalRequest;
import com.smartflow.dto.Document;
import com.smartflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

    private final DocumentService documentService;

    public ApprovalController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Void> createDocument(@RequestBody CreateApprovalRequest request, Principal principal) {
        String username = principal.getName();
        documentService.createDocument(request, username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approveDocument(
            @PathVariable Long id,
            Principal principal    // <-- 추가
    ) {
        String username = principal.getName();
        documentService.approveDocument(id, username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectDocument(
            @PathVariable Long id,
            Principal principal
    ) {
        String username = principal.getName();
        documentService.rejectDocument(id, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-documents")
    public ResponseEntity<List<Document>> getMyDocuments(Principal principal) {
        String username = principal.getName();
        List<Document> docs = documentService.getDocumentsByUser(username);
        return ResponseEntity.ok(docs);
    }
}
