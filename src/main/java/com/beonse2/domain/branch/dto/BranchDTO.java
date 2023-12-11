package com.beonse2.domain.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class BranchDTO {

    private Long bId; //고유번호
    private Long memberMid;
    private String address;
    private double lat;//위도
    private double lng;//경도
    private String branchName;//가맹점명
    private String introduction;//지점사 소개
    private List<ImageDTO> imageDTOS;

    @Builder
    public BranchDTO(Long bId, Long memberMid, String address, double lat, double lng,
                     String branchName, String introduction, List<ImageDTO> imageDTOS) {
        this.bId = bId;
        this.memberMid = memberMid;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.branchName = branchName;
        this.introduction = introduction;
        this.imageDTOS = imageDTOS;
    }
}
