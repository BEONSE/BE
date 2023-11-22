package com.beonse2.domain.admin.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.admin.dto.BranchMemberDTO;
import com.beonse2.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/accept/{member-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessMessageDTO> patchAcceptAdmin(@PathVariable("member-id") Long memberId) {
        return ResponseEntity.ok(adminService.updateAcceptAdmin(memberId));
    }

    @PatchMapping("/reject/{member-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessMessageDTO> patchRejectAdmin(@PathVariable("member-id") Long memberId) {
        return ResponseEntity.ok(adminService.updateRejectAdmin(memberId));
    }

    @GetMapping("/branch/member")
    public ResponseEntity<PageResponseDTO> getBranchMember(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(adminService.findBranchMember(page));
    }

    @GetMapping("/branch/result")
    public ResponseEntity<PageResponseDTO> getMemberResult(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(adminService.findMemberResult(page));
    }
}
