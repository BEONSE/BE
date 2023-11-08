package com.beonse2.domain.coupon.dto;

import lombok.Getter;

@Getter
public class CouponRequestDTO {

    private Long branchBid;
    private String type;
    private int price;
    private boolean isUsed;

}
