package com.beonse2.domain.coupon.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.service.CouponService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @PatchMapping("/mypage/coupons/{coupon-id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessMessageDTO> patchCoupon(@PathVariable("coupon-id") Long couponId,
                                                         @RequestHeader("Authorization") String accessToken,
                                                         @RequestBody CouponRequestDTO couponRequestDTO) {
        log.info("여기", couponRequestDTO.getBranchName());
        return couponService.updateCoupon(couponId, accessToken, couponRequestDTO);
    }
}
