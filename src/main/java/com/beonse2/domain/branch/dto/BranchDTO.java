package com.beonse2.domain.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BranchDTO {

    private Long bId; //고유번호
    private Long memberMid;
    private String email;
    private String branchName;//가맹점명
    private String isApproval; //가입 승인
    private String introduction;//지점사 소개
    private List<ImageDTO> imageDTOS;

    @Builder
    public BranchDTO(Long bId, Long memberMid, String email, List<ImageDTO> imageDTOS,
                     String branchName, String isApproval, String introduction) {
        this.bId = bId;
        this.memberMid = memberMid;
        this.email = email;
        this.imageDTOS = imageDTOS;
        this.branchName = branchName;
        this.isApproval = isApproval;
        this.introduction = introduction;
    }
}
