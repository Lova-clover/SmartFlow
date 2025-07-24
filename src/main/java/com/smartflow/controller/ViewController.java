package com.smartflow.controller;

import com.smartflow.domain.ApprovalDocument;
import com.smartflow.dto.Document;
import com.smartflow.service.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ViewController {

    private final DocumentService documentService;

    public ViewController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "false") boolean includeShared,
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        Page<Document> docsPage = documentService.getPagedDocumentsByUser(username, keyword, includeShared, page);
        List<Document> docs = docsPage.getContent();

        model.addAttribute("page", docsPage);
        model.addAttribute("documents", docs);
        model.addAttribute("username", username);
        model.addAttribute("isAdmin", checkAdmin());
        model.addAttribute("keyword", keyword);
        model.addAttribute("includeShared", includeShared);
        return "dashboard";
    }

    @GetMapping("/document/create")
    public String createDoc() {
        return "document_create";
    }

    private boolean checkAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/document/{id}")
    public String viewDocumentDetail(@PathVariable Long id, Model model) {
        Document doc = documentService.getDocumentById(id);  // id로 문서 조회
        if (doc == null) {
            return "redirect:/dashboard";  // 없으면 대시보드로 리다이렉트
        }
        model.addAttribute("document", doc);
        return "document_detail";  // 상세페이지 뷰 이름
    }
}