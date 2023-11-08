package com.beonse2.domain.coupon.service;

import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.coupon.vo.CouponVO;
import com.beonse2.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponMapper couponMapper;

    @Transactional
    public ResponseEntity<CouponResponseDTO> createCoupon(MemberDTO memberDTO, CouponRequestDTO couponRequestDTO) {

        CouponVO couponVO = CouponVO.builder()
                .memberMid(1L)
                .branchBid(1L)
                .type(couponRequestDTO.getType())
                .price(3000)
                .isUsed(false)
                .build();

        couponMapper.saveCoupon(CouponVO.builder()
                .memberMid(1L)
                .branchBid(1L)
                .type(couponRequestDTO.getType())
                .price(3000)
                .isUsed(false)
                .build());

        return ResponseEntity.ok(CouponResponseDTO.builder()
                .couponVO(couponVO)
                .build());
    }
}
