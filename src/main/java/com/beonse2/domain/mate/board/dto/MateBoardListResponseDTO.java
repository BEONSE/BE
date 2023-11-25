package com.beonse2.domain.mate.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MateBoardListResponseDTO {

    private int rnum;
    private Long mbid;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;
    private String nickname;
    private String branchname;
    private int paymentAmount;
    private int commentCount;
    private int grade;

    @Builder
    public MateBoardListResponseDTO(int rnum, Long mbid, String title, String content, Timestamp modifiedAt, String nickname, String branchname, int paymentAmount) {
        this.rnum = rnum;
        this.mbid = mbid;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.nickname = nickname;
        this.branchname = branchname;
        this.paymentAmount = paymentAmount;
    }

    public void updateCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
