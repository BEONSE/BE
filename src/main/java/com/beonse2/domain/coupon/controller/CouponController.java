package com.beonse2.domain.coupon.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.service.CouponService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postCoupon(@RequestHeader("Authorization") String accessToken,
                                                        @RequestBody CouponRequestDTO couponRequestDTO) {
        return couponService.createCoupon(accessToken, couponRequestDTO);
    }

    @GetMapping("/mypage/coupons")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CouponResponseDTO>> getCouponList(@RequestHeader("Authorization") String accessToken) {
        return couponService.findCouponList(accessToken);
    }
}
