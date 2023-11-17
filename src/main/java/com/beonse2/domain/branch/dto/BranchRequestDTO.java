package com.beonse2.domain.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BranchRequestDTO {

    private Long bid;

    private String email; //email -> id

    private String name; //대표자명

    private String password; //비밀번호

    private String address;//주소

    private String branchName;//가맹점명

    private String introduction;//지점사 소개

    @Builder
    public BranchRequestDTO(String email, String name, String password, String address, String branchName, String introduction) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.branchName = branchName;
        this.introduction = introduction;
    }
}
