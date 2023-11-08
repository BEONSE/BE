package com.beonse2.domain.coupon.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.coupon.vo.CouponVO;
import com.beonse2.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponMapper couponMapper;

    @Transactional
    public ResponseEntity<CouponResponseDTO> createCoupon(MemberDTO memberDTO, CouponRequestDTO couponRequestDTO) {

        CouponVO couponVO = CouponVO.builder()
                .memberMid(1L)
                .type(couponRequestDTO.getType())
                .price(3000)
                .isUsed(false)
                .build();

        couponMapper.saveCoupon(CouponVO.builder()
                .memberMid(1L)
                .type(couponRequestDTO.getType())
                .price(3000)
                .isUsed(false)
                .build());

        return ResponseEntity.ok(CouponResponseDTO.builder()
                .couponVO(couponVO)
                .build());
    }

    public ResponseEntity<List<CouponResponseDTO>> findCouponList(MemberDTO memberDTO) {

        List<CouponVO> couponVOS = couponMapper.findAllByMemberMidOrderByPaymentDateDesc(memberDTO.getMid());

        if (couponVOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }

        List<CouponResponseDTO> couponResponseDTOS = new ArrayList<>();
        for (CouponVO couponVO : couponVOS) {
            CouponResponseDTO couponResponseDTO = CouponResponseDTO.builder()
                    .couponVO(couponVO)
                    .build();

            couponResponseDTOS.add(couponResponseDTO);
        }

        return ResponseEntity.ok(couponResponseDTOS);
    }

    @Transactional
    public ResponseEntity<CouponResponseDTO> updateCoupon(MemberDTO memberDTO, Long couponId) {

        couponMapper.updateCoupon(couponId);

        CouponVO couponVO = couponMapper.findById(couponId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COUPON)
        );

        return ResponseEntity.ok(CouponResponseDTO.builder()
                .couponVO(couponVO)
                .build());
    }
}
