package com.beonse2.domain.coupon.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.service.CouponService;
import com.beonse2.domain.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branchies")
public class CouponController {

    private final CouponService couponService;


    @Operation(summary = "쿠폰 생성", description = "쿠폰 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/coupons")
    public ResponseEntity<SuccessMessageDTO> postCoupon(@AuthenticationPrincipal MemberDTO memberDTO,
                                                        @RequestBody CouponRequestDTO couponRequestDTO) {
        return couponService.createCoupon(memberDTO, couponRequestDTO);
    }
}
