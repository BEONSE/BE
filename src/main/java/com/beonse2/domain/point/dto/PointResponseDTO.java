package com.beonse2.domain.point.dto;

import com.beonse2.domain.point.vo.PointVO;
import lombok.Getter;

@Getter
public class PointResponseDTO {

    private Long pid;
    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;


    public PointResponseDTO(PointVO pointVO) {
        this.pid = pointVO.getPid();
        this.points = pointVO.getPoints();
        this.paymentPrice = pointVO.getPaymentPrice();
        this.cardName = pointVO.getCardName();
        this.cardNumber = pointVO.getCardNumber();
    }
}
