package com.beonse2.domain.coupon.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class CouponVO {

    private Long cid;
    private Long memberMid;
    private String type;
    private int price;
    private boolean isUsed;
    private Timestamp paymentDate;

    @Builder
    public CouponVO(Long memberMid, String type, int price, boolean isUsed) {
        this.memberMid = memberMid;
        this.type = type;
        this.price = price;
        this.isUsed = isUsed;
    }
}
