package com.beonse2.domain.admin.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/accept/{member-id}")
    public ResponseEntity<SuccessMessageDTO> patchAcceptAdmin(@PathVariable("member-id") Long memberId) {
        return ResponseEntity.ok(adminService.updateAcceptAdmin(memberId));
    }

    @PatchMapping("/reject/{member-id}")
    public ResponseEntity<SuccessMessageDTO> patchRejectAdmin(@PathVariable("member-id") Long memberId) {
        return ResponseEntity.ok(adminService.updateRejectAdmin(memberId));
    }


    @GetMapping("/branch/member")
    public ResponseEntity<PageResponseDTO> getBranchMemberPage(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(adminService.findBranchMemberPage(page));
    }

    @GetMapping("/branch/result")
    public ResponseEntity<PageResponseDTO> getMemberResultPage(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(adminService.findMemberResultPage(page));
    }

    @GetMapping("/member")
    public ResponseEntity<PageResponseDTO> getMemberPage(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(adminService.findMemberPage(page));
    }

    @GetMapping("/payments")
    public ResponseEntity<PageResponseDTO> getPaymentPage(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(adminService.findPaymentPage(page));
    }
}
