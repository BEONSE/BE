package com.beonse2.domain.myPage.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class MyPage {

    private Long mid;

    private String nickname;

    private File image;

    private int paymentAmount;

    private int pointAmount;

    private int grade;

    @Builder
    public MyPage(String nickname, File image, int paymentAmount, int pointAmount) {
        this.nickname = nickname;
        this.image = image;
        this.paymentAmount = paymentAmount;
        this.pointAmount = pointAmount;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
