package com.beonse2.domain.mycoupon.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MyCouponVO {

    private Long mcpid;
    private Long memberMid;
    private Long branchBid;
    private String type;
    private int price;
    private boolean isUsed;
    private Timestamp paymentDate;

    @Builder
    public MyCouponVO(Long memberMid, Long branchBid, String type, int price, boolean isUsed) {
        this.memberMid = memberMid;
        this.branchBid = branchBid;
        this.type = type;
        this.price = price;
        this.isUsed = isUsed;
    }
}
