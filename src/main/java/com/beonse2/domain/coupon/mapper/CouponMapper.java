package com.beonse2.domain.coupon.mapper;

import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.vo.CouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CouponMapper {
    void saveCoupon(CouponVO couponVO);

    List<CouponResponseDTO> findAllCoupon(Long mid);
}
