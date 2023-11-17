package com.beonse2.domain.coupon.mapper;

import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.vo.CouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface CouponMapper {
    void saveCoupon(CouponVO couponVO);

    List<CouponResponseDTO> findAllCoupon(Long mid);

    Optional<CouponResponseDTO> findById(Long couponId);

    void updateCoupon(Map<String, Object> searchMap);

    List<CouponResponseDTO> findUseAllCoupon(String branchName);
}
