package com.beonse2.domain.point.dto;

import com.beonse2.domain.point.vo.PointVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class PointResponseDTO {

    private Long pid;
    private int points;
    private int paymentPrice;
    private String cardName;
    private String cardNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp paymentDate;

    @Builder
    public PointResponseDTO(PointVO pointVO) {
        this.pid = pointVO.getPid();
        this.points = pointVO.getPoints();
        this.paymentPrice = pointVO.getPaymentPrice();
        this.cardName = pointVO.getCardName();
        this.cardNumber = pointVO.getCardNumber();
        this.paymentDate = pointVO.getPaymentDate();
    }
}
