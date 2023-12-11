package com.beonse2.domain.branch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ImageDTO {

    private Long bmid;
    private Long branchBid;
    private MultipartFile image;
    private byte[] imageData;
    private String originalFileName;
    private String imageType;

    @Builder
    public ImageDTO(Long branchBid, MultipartFile image, byte[] imageData, String originalFileName, String imageType) {
        this.branchBid = branchBid;
        this.image = image;
        this.imageData = imageData;
        this.originalFileName = originalFileName;
        this.imageType = imageType;
    }
}
