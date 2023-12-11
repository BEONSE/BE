package com.beonse2.domain.myPage.vo;

import com.beonse2.domain.member.vo.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class MyPage {

    private Long mid;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private MultipartFile image;
    private byte[] imageData;
    private String originalFileName;
    private String imageType;
    private String address;
    private int paymentAmount;
    private int pointAmount;
    private int grade;
    private Role role;

    @Builder
    public MyPage(Long mid, String email, String password, String name, String nickname, MultipartFile image, String originalFileName,
                  String imageType, byte[] imageData, String address, int paymentAmount, int pointAmount, int grade, Role role) {
        this.mid = mid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.image = image;
        this.imageData = imageData;
        this.originalFileName = originalFileName;
        this.imageType = imageType;
        this.address = address;
        this.paymentAmount = paymentAmount;
        this.pointAmount = pointAmount;
        this.grade = grade;
        this.role = role;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
