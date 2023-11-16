package com.beonse2.domain.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CouponResponseDTO {

    private Long cid;
    private Long memberMid;
    private String type;
    private String price;
    private boolean isUsed;
    private Timestamp paymentDate;

    @Builder
    public CouponResponseDTO(Long cid, Long memberMid, String type, String price, boolean isUsed, Timestamp paymentDate) {
        this.cid = cid;
        this.memberMid = memberMid;
        this.type = type;
        this.price = price;
        this.isUsed = isUsed;
        this.paymentDate = paymentDate;
    }
}
