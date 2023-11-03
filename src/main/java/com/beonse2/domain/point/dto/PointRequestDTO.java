package com.beonse2.domain.point.dto;

import lombok.Getter;

@Getter
public class PointRequestDTO {

    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;

}
