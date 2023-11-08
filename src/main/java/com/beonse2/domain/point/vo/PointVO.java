package com.beonse2.domain.point.vo;

import com.beonse2.domain.point.dto.PointRequestDTO;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PointVO {

    private Long pid;
    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;
    private Timestamp paymentDate;

    @Builder
    public PointVO(Long pid, int points, int paymentPrice, String cardName, String cardNumber, Timestamp paymentDate) {
        this.pid = pid;
        this.points = points;
        this.paymentPrice = paymentPrice;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.paymentDate = paymentDate;
    }
}
