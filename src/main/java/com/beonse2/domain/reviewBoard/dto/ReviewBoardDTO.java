package com.beonse2.domain.reviewBoard.dto;

import lombok.*;

import java.io.File;
import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewBoardDTO {

    private Long rbId; //고유 번호
    private String title;
    private String content;
    private String writer; //작성자
    private String branchName; //지점명
    private boolean status;
    private File image;
    private LocalDateTime createdAt;//작성일
    private LocalDateTime modifiedAt;//수정일

}
