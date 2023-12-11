package com.beonse2.domain.branch.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class Branch {

    private Long bid;
    private Long memberId;
    private String name;
    private String branchName;
    private String address;
    private String password;
    private double lat;
    private double lng;
    private MultipartFile image;
    private String originalFileName;
    private String imageType;
    private byte[] imageData;
    private String introduction;//지점사 소개

    @Builder
    public Branch(Long bid, Long memberId, String name, String branchName, String address, String password, double lat, double lng,
                  MultipartFile image, String originalFileName, String imageType, byte[] imageData, String introduction) {
        this.bid = bid;
        this.memberId = memberId;
        this.name = name;
        this.branchName = branchName;
        this.address = address;
        this.password = password;
        this.lat = lat;
        this.lng = lng;
        this.image = image;
        this.originalFileName = originalFileName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.introduction = introduction;
    }
}
