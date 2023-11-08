package com.beonse2.domain.mycoupon.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.domain.mycoupon.dto.MyCouponRequestDTO;
import com.beonse2.domain.mycoupon.dto.MyCouponResponseDTO;
import com.beonse2.domain.mycoupon.mapper.MyCouponMapper;
import com.beonse2.domain.mycoupon.vo.MyCouponVO;
import com.beonse2.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCouponService {

    private final MyCouponMapper couponMapper;

    @Transactional
    public ResponseEntity<MyCouponResponseDTO> createCoupon(MemberDTO memberDTO, MyCouponRequestDTO couponRequestDTO) {

        MyCouponVO couponVO = MyCouponVO.builder()
                .memberMid(1L)
                .branchBid(1L)
                .type(couponRequestDTO.getType())
                .price(3000)
                .isUsed(false)
                .build();

        couponMapper.saveCoupon(MyCouponVO.builder()
                .memberMid(1L)
                .branchBid(1L)
                .type(couponRequestDTO.getType())
                .price(3000)
                .isUsed(false)
                .build());

        return ResponseEntity.ok(MyCouponResponseDTO.builder()
                .couponVO(couponVO)
                .build());
    }

    public ResponseEntity<List<MyCouponResponseDTO>> findCouponList(MemberDTO memberDTO, Long couponId) {

        List<MyCouponVO> couponVOS = couponMapper.findAllByMemberIdAndCouponIdOrderByPaymentDateDesc(couponId);

        if (couponVOS.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_COUPON);
        }

        List<MyCouponResponseDTO> couponResponseDTOS = new ArrayList<>();
        for (MyCouponVO couponVO : couponVOS) {
            MyCouponResponseDTO couponResponseDTO = MyCouponResponseDTO.builder()
                    .couponVO(couponVO)
                    .build();

            couponResponseDTOS.add(couponResponseDTO);
        }

        return ResponseEntity.ok(couponResponseDTOS);
    }
}
