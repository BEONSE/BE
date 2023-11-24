package com.beonse2.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class PaymentDTO {

    private int rnum;
    private Long pid;
    private Long mid;
    private String name;
    private String nickname;
    private String address;
    private String price;
    private String points;
    private String cardName;
    private String cardNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:dd", timezone = "Asia/Seoul")
    private Timestamp paymentDate;

}
