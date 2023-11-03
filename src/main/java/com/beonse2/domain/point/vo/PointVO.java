package com.beonse2.domain.point.vo;

import com.beonse2.domain.point.dto.PointRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PointVO {

    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;

    @Builder

    public PointVO(PointRequestDTO pointRequestDTO) {
        this.points = pointRequestDTO.getPoints();
        this.paymentPrice = pointRequestDTO.getPaymentPrice();
        this.cardName = pointRequestDTO.getCardName();
        this.cardNumber = pointRequestDTO.getCardNumber();
    }
}
