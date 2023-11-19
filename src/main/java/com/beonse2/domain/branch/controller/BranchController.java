package com.beonse2.domain.branch.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchDTO;
import com.beonse2.domain.branch.dto.BranchListDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.sevice.BranchService;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.service.CouponService;
import com.beonse2.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    private final MemberService memberService;

    private final CouponService couponService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/joinbranch")
    public ResponseEntity<SuccessMessageDTO> save(@RequestBody BranchRequestDTO branchRequestDTO) {
        branchService.save(branchRequestDTO);
        System.out.println("branchRequestDTO : " + branchRequestDTO);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("성공적으로 회원가입이 완료되었습니다.")
                .build());
    }

    @GetMapping("/branches") //전체 쿠폰 사용 내역 조회
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<List<CouponResponseDTO>> findUseAllCoupon(@RequestHeader("Authorization") String accessToken) {
        return couponService.findUseAllCoupon(accessToken);
    }

    @GetMapping("/branches/info/{branch-id}") //가맹점 상세 조회
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BranchDTO> findBranch(@PathVariable("branch-id") Long branchId,
                                                @RequestHeader("Authorization") String accessToken) {
        return branchService.findBranch(branchId, accessToken);
    }

    @GetMapping("/branches/{member-id}") //단일 회원 쿠폰 결제
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<List<CouponResponseDTO>> findUseMemberCoupon(@PathVariable("member-id") Long memberId,
                                                                       @RequestHeader("Authorization") String accessToken) {
        return couponService.findUseMemberCoupon(memberId, accessToken);
    }

    @GetMapping("/branches/names")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<String>> findBranchNames() {
        return branchService.findBranchNames();
    }

    @GetMapping("/branches/map")
    public ResponseEntity<List<BranchListDTO>> findByAllBranch() {
        return branchService.findByAllBranch();
    }

    @GetMapping("/branches/search")
    public ResponseEntity<List<BranchListDTO>> getBranchSearch(String name) {
        return branchService.findBranchSearch(name);
    }

    @PatchMapping("/branches/info") //단일 회원 쿠폰 결제
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<SuccessMessageDTO> patchBranch(@RequestPart List<MultipartFile> image,
                                                         @RequestPart BranchRequestDTO branchRequestDTO,
                                                         @RequestHeader("Authorization") String accessToken) throws IOException {
        return branchService.updateBranch(accessToken, image, branchRequestDTO);
    }
}
