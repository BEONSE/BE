package com.beonse2.domain.reviewBoard.dto;

import lombok.*;

import java.io.File;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewBoardDTO {

    private Long rbId; //고유 번호
    private Long memberId;
    private Long branchId;
    private String title;
    private String content;
    private String writer; //작성자
    private String branchName; //지점명
    private boolean status;
    private File image;
    private Timestamp createdAt;//작성일
    private Timestamp modifiedAt;//수정일

    @Builder
    public ReviewBoardDTO(Long rbId, String title, String content, String writer, String branchName, boolean status, File image, Timestamp createdAt, Timestamp modifiedAt) {
        this.rbId = rbId;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.branchName = branchName;
        this.status = status;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
