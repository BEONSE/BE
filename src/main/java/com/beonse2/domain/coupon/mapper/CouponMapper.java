package com.beonse2.domain.coupon.mapper;

import com.beonse2.domain.coupon.vo.CouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CouponMapper {

    void saveCoupon(CouponVO couponVO);

    List<CouponVO> findAllByMemberMidOrderByPaymentDateDesc(Long couponId);

    Optional<CouponVO> findById(Long couponId);

    void updateCoupon(Long couponId);
}
