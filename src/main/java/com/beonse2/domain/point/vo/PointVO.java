package com.beonse2.domain.point.vo;

import com.beonse2.domain.point.dto.PointRequestDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PointVO {

    private Long pid;
    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;

    @Builder
    public PointVO(int points, PointRequestDTO pointRequestDTO) {
        this.points = points;
        this.paymentPrice = pointRequestDTO.getPaymentPrice();
        this.cardName = pointRequestDTO.getCardName();
        this.cardNumber = pointRequestDTO.getCardNumber();
    }
}
