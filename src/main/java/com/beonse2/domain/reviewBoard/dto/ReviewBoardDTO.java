package com.beonse2.domain.reviewBoard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ReviewBoardDTO {

    private Long rbId; //고유 번호
    private int rnum;
    private Long memberMid;
    private Long branchBid;
    private Long couponCid;
    private String title;
    private String content;
    private String writer; //작성자
    private String branchName; //지점명
    private boolean status;
    private MultipartFile image;
    private String originalFileName;
    private String imageType;
    private byte[] imageData;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;//작성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;//수정일

    @Builder
    public ReviewBoardDTO(Long rbId, Long memberMid, Long branchBid, Long couponCid, String title,
                          String content, String writer, String branchName, boolean status, MultipartFile image,
                          String originalFileName, String imageType, byte[] imageData, Timestamp createdAt, Timestamp modifiedAt) {
        this.rbId = rbId;
        this.memberMid = memberMid;
        this.branchBid = branchBid;
        this.couponCid = couponCid;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.branchName = branchName;
        this.status = status;
        this.image = image;
        this.originalFileName = originalFileName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
