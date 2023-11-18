package com.beonse2.domain.myPage.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class MyPage {

    private Long mid;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private File image;

    private String address;

    private int paymentAmount;

    private int pointAmount;

    private int grade;

    @Builder
    public MyPage(String email, String password, String name, String nickname, File image, String address, int paymentAmount, int pointAmount) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.image = image;
        this.address = address;
        this.paymentAmount = paymentAmount;
        this.pointAmount = pointAmount;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
