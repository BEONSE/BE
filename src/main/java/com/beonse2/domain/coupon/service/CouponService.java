package com.beonse2.domain.coupon.service;

import com.beonse2.config.utils.success.SuccessCode;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.coupon.vo.CouponVO;
import com.beonse2.domain.member.dto.MemberDTO;
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
    public ResponseEntity<SuccessMessageDTO> createCoupon(MemberDTO memberDTO, CouponRequestDTO couponRequestDTO) {

        CouponVO couponVO = CouponVO.builder()
                .branchBid(couponRequestDTO.getBranchBid())
                .type(couponRequestDTO.getType())
                .price(couponRequestDTO.getPrice())
                .build();

        couponMapper.saveCoupon(couponVO);

        return ResponseEntity.ok(new SuccessMessageDTO(SuccessCode.SUCCESS_SAVE_COUPON));
    }
}
