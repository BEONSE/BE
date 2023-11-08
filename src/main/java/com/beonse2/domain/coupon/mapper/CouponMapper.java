package com.beonse2.domain.coupon.mapper;

import com.beonse2.domain.coupon.vo.CouponVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponMapper {

    void saveCoupon(CouponVO couponVO);
}
