package com.beonse2.domain.branch.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchDTO;
import com.beonse2.domain.branch.dto.BranchListDTO;
import com.beonse2.domain.branch.dto.BranchNameDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.sevice.BranchService;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;
    private final CouponService couponService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/join/branch")
    public ResponseEntity<SuccessMessageDTO> postBranch(@RequestBody BranchRequestDTO branchRequestDTO) {
        return ResponseEntity.ok(branchService.createBranch(branchRequestDTO));
    }

    @GetMapping("/branches/coupons") //전체 쿠폰 사용 내역 조회
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<PageResponseDTO> getUseCoupons(@RequestHeader("Authorization") String accessToken,
                                                         @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(couponService.findUseAllCoupon(accessToken, page));
    }

    @GetMapping("/branches") // 가맹점 전체 조회
    public ResponseEntity<List<BranchListDTO>> getBranches() {
        return ResponseEntity.ok(branchService.findByAllBranch());
    }

    @GetMapping("/branches/{branch-id}") //가맹점 상세 조회
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BranchDTO> getBranch(@PathVariable("branch-id") Long branchId) {
        return ResponseEntity.ok(branchService.findBranch(branchId));
    }

    @GetMapping("/branches/names")
    public ResponseEntity<List<BranchNameDTO>> getBranchNames() {
        return ResponseEntity.ok(branchService.findBranchNames());
    }

    @GetMapping("/branches/search")
    public ResponseEntity<List<BranchListDTO>> getBranchSearch(String name) {
        return ResponseEntity.ok(branchService.findBranchSearch(name));
    }

    @PatchMapping("/branches/info")
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<SuccessMessageDTO> patchBranch(@RequestPart(required = false) List<MultipartFile> image,
                                                         @RequestPart BranchRequestDTO branchRequestDTO,
                                                         @RequestHeader("Authorization") String accessToken) throws IOException {
        return ResponseEntity.ok(branchService.updateBranch(accessToken, image, branchRequestDTO));
    }


}
