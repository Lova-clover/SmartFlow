package com.smartflow.controller;

import com.smartflow.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/approval")
public class ApprovalViewController {

    private final DocumentService documentService;

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveDocument(@PathVariable Long id, Principal principal) {
        documentService.approveDocument(id, principal.getName());
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public String rejectDocument(@PathVariable Long id, Principal principal) {
        documentService.rejectDocument(id, principal.getName());
        return "redirect:/dashboard";
    }
}
