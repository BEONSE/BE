package com.beonse2.domain.coupon.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.coupon.vo.CouponVO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponMapper couponMapper;
    private final MemberMapper memberMapper;
    private final BranchMapper branchMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<SuccessMessageDTO> createCoupon(String accessToken, CouponRequestDTO couponRequestDTO) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        CouponVO couponVO = CouponVO.builder()
                .memberMid(findMember.getMid())
                .type(couponRequestDTO.getType())
                .price(couponRequestDTO.getPrice())
                .build();

        for (int i = 0; i < couponRequestDTO.getQuantity(); i++) {
            couponMapper.saveCoupon(couponVO);
        }

        int pointPayment = findMember.getPointAmount() - couponRequestDTO.getPrice();
        findMember.updateAmounts(0, pointPayment);

        memberMapper.updatePoint(findMember);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("쿠폰 구매 성공.")
                .build());
    }

    public ResponseEntity<List<CouponResponseDTO>> findCouponList(String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        List<CouponResponseDTO> couponResponseDTOS = couponMapper.findAllCoupon(findMember.getMid());

        if (couponResponseDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }

        return ResponseEntity.ok(couponResponseDTOS);
    }

    @Transactional
    public ResponseEntity<SuccessMessageDTO> updateCoupon(Long couponId, String accessToken, CouponRequestDTO couponRequestDTO) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO memberDTO = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        CouponResponseDTO findCoupon = couponMapper.findById(couponId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COUPON)
        );

        if (!memberDTO.getMid().equals(findCoupon.getMemberMid())) {
            throw new CustomException(NOT_MATCH_USER);
        }

        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("couponId", couponId);
        searchMap.put("branchName", couponRequestDTO.getBranchName());

        couponMapper.updateCoupon(searchMap);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("쿠폰 사용 완료")
                .build());
    }

    public ResponseEntity<List<CouponResponseDTO>> findUseAllCoupon(String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        String branchName = findMember.getNickname();

        List<CouponResponseDTO> findCoupon = couponMapper.findUseAllCoupon(branchName);

        if (findMember.getNickname().isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }

        return ResponseEntity.ok(findCoupon);
    }

    public ResponseEntity<List<CouponResponseDTO>> findUseMemberCoupon(Long memberId, String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        String branchName = findMember.getNickname();

        List<CouponResponseDTO> findCoupon = couponMapper.findUseAllCoupon(branchName);

        for (CouponResponseDTO couponResponseDTO : findCoupon) {
            if (!couponResponseDTO.getMemberMid().equals(memberId)) {
                throw new CustomException(NOT_FOUND_MEMBER);
            }
        }

        if (findMember.getNickname().isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }
        return ResponseEntity.ok(findCoupon);
    }

}
