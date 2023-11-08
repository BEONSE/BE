package com.beonse2.domain.coupon.dto;

import com.beonse2.domain.coupon.vo.CouponVO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CouponResponseDTO {

    private Long mcid;
    private Long memberMid;
    private String type;
    private int price;
    private boolean isUsed;
    private Timestamp paymentDate;

    @Builder
    public CouponResponseDTO(CouponVO couponVO) {
        this.mcid = couponVO.getMcid();
        this.memberMid = couponVO.getMemberMid();
        this.type = couponVO.getType();
        this.price = couponVO.getPrice();
        this.isUsed = couponVO.isUsed();
        this.paymentDate = couponVO.getPaymentDate();
    }
}
