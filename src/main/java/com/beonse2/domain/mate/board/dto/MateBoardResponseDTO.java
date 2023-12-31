package com.beonse2.domain.mate.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MateBoardResponseDTO {

    private Long mbid;
    private String title;
    private String content;
    private String nickname;
    private String branchName;
    private int paymentAmount;
    private byte[] imageData;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;
    private int commentCount;
    private int grade;

    @Builder
    public MateBoardResponseDTO(Long mbid, String title, String content, String nickname, String branchName, int paymentAmount, byte[] imageData, Timestamp createdAt, Timestamp modifiedAt) {
        this.mbid = mbid;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.branchName = branchName;
        this.paymentAmount = paymentAmount;
        this.imageData = imageData;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void updateCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
