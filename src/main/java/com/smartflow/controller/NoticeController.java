package com.smartflow.controller;

import com.smartflow.dto.NoticeDto;
import com.smartflow.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("notices", noticeService.findAll());
        return "notice/list";
    }

    @GetMapping("/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String form(Model model) {
        model.addAttribute("notice", new NoticeDto());
        return "notice/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String create(@ModelAttribute NoticeDto dto, Principal principal) {
        noticeService.create(dto, principal.getName());
        return "redirect:/notice";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.findById(id));
        return "notice/detail";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        noticeService.delete(id);
        return "redirect:/notice";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.findById(id));
        return "notice/edit";
    }

    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@PathVariable Long id, @ModelAttribute NoticeDto dto) {
        noticeService.update(id, dto);
        return "redirect:/notice/" + id;
    }
}