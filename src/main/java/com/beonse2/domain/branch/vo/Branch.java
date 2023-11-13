package com.beonse2.domain.branch.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Getter
@NoArgsConstructor
public class Branch {

    private Long bid;

    private Long memberId;

    private String name;

    private String address;

    private String image;

    private String introduction;//지점사 소개

    private Timestamp createAt; //생성일
    private Timestamp modifiedAt; //수정일
}
