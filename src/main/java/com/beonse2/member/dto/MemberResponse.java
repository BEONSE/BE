package com.beonse2.member.dto;

import com.beonse2.member.vo.Member;
import com.beonse2.member.vo.enums.Role;
import lombok.Getter;

import java.io.File;

@Getter
public class MemberResponse{ //회원 데이터 조회시 사용

    private Long id;//고유번호
    private String email;//email
    private String nickname;//닉네임
    private String password;//PW
    private String name;//이름
    private String address;//주소
    private Role role;//권한
    private Long paymentAmount;//누적 결제 내역
    private boolean status;//회원 삭제
    private File image;//프로필 사진

    //상세정보를 조회한 후 비밀번호 초기화
    public void clearPassword() {
        this.password = "";
    }
}
