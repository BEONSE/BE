package com.beonse2.domain.mycoupon.dto;

import lombok.Getter;

@Getter
public class MyCouponRequestDTO {

    private Long branchBid;
    private String type;
    private int price;
    private boolean isUsed;

}
