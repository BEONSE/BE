package com.beonse2.domain.mycoupon.mapper;

import com.beonse2.domain.mycoupon.vo.MyCouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyCouponMapper {

    void saveCoupon(MyCouponVO couponVO);

    List<MyCouponVO> findAllByMemberIdAndCouponIdOrderByPaymentDateDesc(Long couponId);
}
