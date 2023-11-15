package com.beonse2.domain.point.vo;

import com.beonse2.domain.point.dto.PointRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class PointVO {

    private Long pid;
    private Long memberMid;
    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;
    private Timestamp paymentDate;

    @Builder
    public PointVO(Long pid, Long memberMid, int points, int paymentPrice, String cardName, String cardNumber, Timestamp paymentDate) {
        this.pid = pid;
        this.memberMid = memberMid;
        this.points = points;
        this.paymentPrice = paymentPrice;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.paymentDate = paymentDate;
    }
}
