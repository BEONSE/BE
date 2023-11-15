package com.beonse2.domain.branch.dto;

import com.beonse2.domain.member.vo.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.File;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class BranchDTO {

    private Long bId; //고유번호

    private Long mId;

    private String email;

    private String branchName;//가맹점명

    private File image; //대표 사진

    private String isApproval; //가입 승인

    private String introduction;//지점사 소개

    @Builder
    public BranchDTO(String email, String branchName, File image, String isApproval, String introduction) {
        this.email = email;
        this.branchName = branchName;
        this.image = image;
        this.isApproval = isApproval;
        this.introduction = introduction;
    }
}
