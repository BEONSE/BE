package com.beonse2.domain.branch.vo;

import lombok.Builder;
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

    @Builder
    public Branch(Long memberId, String name, String address, String image, String introduction) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.image = image;
        this.introduction = introduction;
    }
}
