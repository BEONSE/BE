package com.beonse2.domain.coupon.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CouponVO {

    private Long cid;
    private Long branchBid;
    private String type;
    private int price;

    @Builder
    public CouponVO(Long branchBid, String type, int price) {
        this.branchBid = branchBid;
        this.type = type;
        this.price = price;
    }
}
