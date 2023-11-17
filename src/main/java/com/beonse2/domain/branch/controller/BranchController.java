package com.beonse2.domain.branch.controller;

import com.beonse2.config.service.ResponseService;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.sevice.BranchService;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.service.CouponService;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.dto.TokenDTO;
import com.beonse2.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/branches/members") //회원 관리

    @GetMapping("/branches") //전체 쿠폰 사용 내역 조회
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<List<CouponResponseDTO>> findUseAllCoupon(@RequestHeader("Authorization") String accessToken) {
        return couponService.findUseAllCoupon(accessToken);
    }

//    @GetMapping("/branches/{member-id}") //단일 회원 쿠폰 결제

    @GetMapping("/branches/names")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<String>> findBranchNames() {
        return branchService.findBranchNames();
    }
}
