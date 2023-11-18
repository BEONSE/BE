package com.beonse2.domain.myPage.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@NoArgsConstructor
public class MyPage {

    private Long mid;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private MultipartFile image;
    private String originalFileName;
    private String imageType;
    private String address;
    private int paymentAmount;
    private int pointAmount;
    private int grade;

    @Builder
    public MyPage(Long mid, String email, String password, String name, String nickname, MultipartFile image,
                  String originalFileName, String imageType, String address, int paymentAmount, int pointAmount, int grade) {
        this.mid = mid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.image = image;
        this.originalFileName = originalFileName;
        this.imageType = imageType;
        this.address = address;
        this.paymentAmount = paymentAmount;
        this.pointAmount = pointAmount;
        this.grade = grade;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
