package com.beonse2.domain.myPage.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class MyPage {

    private Long mid;

    private Long pid;

    private String nickname;

    private File image;

    private int paymentAmount;

    private int paymentPrice;

    private String grade;

    private int points;

    @Builder
    public MyPage(String nickname, File image, int paymentAmount, int paymentPrice, String grade, int points) {
        this.nickname = nickname;
        this.image = image;
        this.paymentAmount = paymentAmount;
        this.paymentPrice = paymentPrice;
        this.grade = grade;
        this.points = points;
    }
}
