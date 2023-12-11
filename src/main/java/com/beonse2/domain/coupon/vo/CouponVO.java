package com.beonse2.domain.coupon.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponVO {

    private Long cid;
    private Long memberMid;
    private String type;
    private int price;

    @Builder
    public CouponVO(Long cid, Long memberMid, String type, int price) {
        this.cid = cid;
        this.memberMid = memberMid;
        this.type = type;
        this.price = price;
    }
}
