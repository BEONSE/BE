package com.beonse2.domain.coupon.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postCoupon(@RequestHeader("Authorization") String accessToken,
                                                        @RequestBody CouponRequestDTO couponRequestDTO) {
        return ResponseEntity.ok(couponService.createCoupon(accessToken, couponRequestDTO));
    }

    @GetMapping("/mypage/coupons")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PageResponseDTO> getCouponPage(@RequestHeader("Authorization") String accessToken,
                                                         @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(couponService.findCouponPage(accessToken, page));
    }

    @GetMapping("/mypage/coupons/used")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PageResponseDTO> getUseCouponPage(@RequestHeader(value = "Authorization") String accessToken,
                                                            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(couponService.findUseCouponPage(accessToken, page));
    }

    @PatchMapping("/mypage/coupons/{coupon-id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessMessageDTO> patchCoupon(@PathVariable("coupon-id") Long couponId,
                                                         @RequestHeader("Authorization") String accessToken,
                                                         @RequestBody CouponRequestDTO couponRequestDTO) {
        return ResponseEntity.ok(couponService.updateCoupon(couponId, accessToken, couponRequestDTO));
    }
}
