package com.beonse2.domain.coupon.mapper;

import com.beonse2.domain.coupon.vo.CouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CouponMapper {

    void saveMyCoupon(CouponVO couponVO);

    List<CouponVO> findAllByMemberMidOrderByPaymentDateDesc(Long couponId);
}
