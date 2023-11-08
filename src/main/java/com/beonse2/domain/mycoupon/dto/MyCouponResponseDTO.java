package com.beonse2.domain.mycoupon.dto;

import com.beonse2.domain.mycoupon.vo.MyCouponVO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MyCouponResponseDTO {

    private Long mcpid;
    private Long memberMid;
    private Long branchBid;
    private String type;
    private int price;
    private boolean isUsed;
    private Timestamp paymentDate;

    @Builder
    public MyCouponResponseDTO(MyCouponVO couponVO) {
        this.mcpid = couponVO.getMcpid();
        this.memberMid = couponVO.getMemberMid();
        this.branchBid = couponVO.getBranchBid();
        this.type = couponVO.getType();
        this.price = couponVO.getPrice();
        this.isUsed = couponVO.isUsed();
        this.paymentDate = couponVO.getPaymentDate();
    }
}
