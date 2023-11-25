package com.beonse2.domain.coupon.mapper;

import com.beonse2.config.utils.page.PageRequestBranchName;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.vo.CouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface CouponMapper {
    void saveCoupon(CouponVO couponVO);

    List<CouponResponseDTO> findCouponPages(PageRequestDTO pageRequest);

    Optional<CouponResponseDTO> findById(Long couponId);

    void updateCoupon(Map<String, Object> searchMap);

    List<CouponResponseDTO> findUseAllCoupon(PageRequestBranchName pageRequest);

    void updateCouponWriteReview(CouponResponseDTO couponResponseDTO);

    int getCountCoupons(Long memberId);

    int getCountUseCoupons(Long memberId);

    List<CouponResponseDTO> findUseCouponPages(PageRequestDTO pageRequest);

    int getCountBranchName(String branchName);
}
