package com.beonse2.domain.coupon.controller;

import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.service.CouponService;
import com.beonse2.domain.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "쿠폰 결제", description = "쿠폰 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/payments/coupons")
    public ResponseEntity<CouponResponseDTO> postCoupon(@AuthenticationPrincipal MemberDTO memberDTO,
                                                          @RequestBody CouponRequestDTO couponRequestDTO) {
        return couponService.createCoupon(memberDTO, couponRequestDTO);
    }

    @Operation(summary = "쿠폰 조회", description = "쿠폰 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/mypage/coupons")
    public ResponseEntity<List<CouponResponseDTO>> getCouponList(@AuthenticationPrincipal MemberDTO memberDTO) {
        return couponService.findCouponList(memberDTO);
    }

    @Operation(summary = "쿠폰 사용", description = "쿠폰 사용")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PatchMapping("/mypage/coupons/{coupon-id}")
    public ResponseEntity<CouponResponseDTO> patchCoupon(@AuthenticationPrincipal MemberDTO memberDTO,
                                                         @PathVariable("coupon-id") Long couponId) {
        return couponService.updateCoupon(memberDTO, couponId);
    }

}
