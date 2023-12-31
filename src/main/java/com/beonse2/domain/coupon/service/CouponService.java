package com.beonse2.domain.coupon.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestBranchName;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.coupon.dto.CouponRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.coupon.vo.CouponVO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final JwtUtil jwtUtil;

    @Transactional
    public SuccessMessageDTO createCoupon(String accessToken, CouponRequestDTO couponRequestDTO) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        CouponVO couponVO = CouponVO.builder()
                .memberMid(findMember.getMid())
                .type(couponRequestDTO.getType())
                .price(couponRequestDTO.getPrice())
                .build();

        int pointPayment = findMember.getPointAmount();
        for (int i = 0; i < couponRequestDTO.getQuantity(); i++) {
            couponMapper.saveCoupon(couponVO);
            pointPayment -= couponRequestDTO.getPrice();
        }

        findMember.updateAmounts(0, pointPayment);

        memberMapper.updatePoint(findMember);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("쿠폰 구매 성공.")
                .build();
    }

    public PageResponseDTO findCouponPage(String accessToken, int page) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        int totalRows = couponMapper.getCountCoupons(findMember.getMid());

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(findMember.getMid())
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<CouponResponseDTO> couponResponseDTOS = couponMapper.findCouponPages(pageRequest);

        if (couponResponseDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }

        return PageResponseDTO.builder()
                .content(couponResponseDTOS)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build();
    }

    public PageResponseDTO findUseCouponPage(String accessToken, int page) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        int totalRows = couponMapper.getCountUseCoupons(findMember.getMid());

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(findMember.getMid())
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<CouponResponseDTO> couponResponseDTOS = couponMapper.findUseCouponPages(pageRequest);

        if (couponResponseDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }

        return PageResponseDTO.builder()
                .content(couponResponseDTOS)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build();
    }

    @Transactional
    public SuccessMessageDTO updateCoupon(Long couponId, String accessToken, CouponRequestDTO couponRequestDTO) {

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

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("쿠폰 사용 완료")
                .build();
    }

    public PageResponseDTO findUseAllCoupon(String accessToken, int page) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        String branchName = findMember.getNickname();

        int totalRows = couponMapper.getCountBranchName(branchName);

        PageRequestBranchName pageRequest = PageRequestBranchName.builder()
                .branchName(branchName)
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<CouponResponseDTO> findCoupon = couponMapper.findUseAllCoupon(pageRequest);

        if (findMember.getNickname().isEmpty()) {
            throw new CustomException(NOT_FOUND_COUPON);
        }

        return PageResponseDTO.builder()
                .content(findCoupon)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build();
    }

}
