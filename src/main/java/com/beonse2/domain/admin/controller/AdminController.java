package com.beonse2.domain.admin.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
