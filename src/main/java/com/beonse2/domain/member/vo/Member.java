package com.beonse2.domain.member.vo;

import com.beonse2.domain.member.vo.enums.Approval;
import com.beonse2.domain.member.vo.enums.Role;
import lombok.*;


import java.io.File;
import java.security.Timestamp;


@Getter
@NoArgsConstructor
public class Member{

    private Long mid;//고유번호
    private String email;//email
    private String nickname;//닉네임
    private String password;//PW
    private String name;//이름
    private String address;//주소
    private Role role;//권한
    private int paymentAmount;//누적 결제 내역
    private boolean status;//회원 삭제
    private File image;//프로필 사진
    private Approval isApproval;//승인 여부
    private Timestamp createdAt;//생성일
    private Timestamp modifiedAt;//수정일

}
