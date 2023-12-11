package com.beonse2.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MaxPaymentDTO {

    private Long mid;
    private String nickname;
    private int paymentAmount;
    private int pointAmount;

}
