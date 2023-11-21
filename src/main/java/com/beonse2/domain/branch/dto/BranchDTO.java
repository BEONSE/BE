package com.beonse2.domain.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BranchDTO {

    private Long bId; //고유번호
    private Long memberMid;
    private String email;
    private String address;
    private double lat;
    private double lng;
    private String branchName;//가맹점명
    private String isApproval; //가입 승인
    private String introduction;//지점사 소개
    private List<ImageDTO> imageDTOS;

    @Builder
    public BranchDTO(Long bId, Long memberMid, String email, String address, double lat, double lng,
                     String branchName, String isApproval, String introduction, List<ImageDTO> imageDTOS) {
        this.bId = bId;
        this.memberMid = memberMid;
        this.email = email;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.branchName = branchName;
        this.isApproval = isApproval;
        this.introduction = introduction;
        this.imageDTOS = imageDTOS;
    }
}
