package com.beonse2.domain.coupon.dto;

import com.beonse2.domain.coupon.vo.CouponVO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CouponResponseDTO {

    private Long cid;
    private Long memberMid;
    private Long branchBid;
    private String type;
    private int price;
    private boolean isUsed;
    private Timestamp paymentDate;

    @Builder
    public CouponResponseDTO(CouponVO couponVO) {
        this.cid = couponVO.getCid();
        this.memberMid = couponVO.getMemberMid();
        this.branchBid = couponVO.getBranchBid();
        this.type = couponVO.getType();
        this.price = couponVO.getPrice();
        this.isUsed = couponVO.isUsed();
        this.paymentDate = couponVO.getPaymentDate();
    }
}
