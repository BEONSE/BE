package com.beonse2.domain.mycoupon.controller;

import com.beonse2.domain.mycoupon.dto.MyCouponRequestDTO;
import com.beonse2.domain.mycoupon.dto.MyCouponResponseDTO;
import com.beonse2.domain.mycoupon.service.MyCouponService;
import com.beonse2.member.dto.MemberDTO;
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
public class MyCouponController {

    private final MyCouponService couponService;

    @Operation(summary = "쿠폰 결제", description = "쿠폰 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/payments/coupons")
    public ResponseEntity<MyCouponResponseDTO> postCoupon(@AuthenticationPrincipal MemberDTO memberDTO,
                                                          @RequestBody MyCouponRequestDTO couponRequestDTO) {
        return couponService.createCoupon(memberDTO, couponRequestDTO);
    }

    @Operation(summary = "쿠폰 조회", description = "쿠폰 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/mypage/coupons/{coupon-cid}")
    public ResponseEntity<List<MyCouponResponseDTO>> getCouponList(@AuthenticationPrincipal MemberDTO memberDTO,
                                                                   @PathVariable("coupon-cid") Long couponId) {
        return couponService.findCouponList(memberDTO, couponId);
    }
}
