package com.beonse2.domain.mate.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MateBoardListResponseDTO {

    private Long mbid;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;
    private String nickname;
    private int paymentAmount;
    private int commentCount;

    @Builder
    public MateBoardListResponseDTO(Long mbid, String title, Timestamp modifiedAt, String nickname, int paymentAmount) {
        this.mbid = mbid;
        this.title = title;
        this.modifiedAt = modifiedAt;
        this.nickname = nickname;
        this.paymentAmount = paymentAmount;
    }

    public void updateCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
