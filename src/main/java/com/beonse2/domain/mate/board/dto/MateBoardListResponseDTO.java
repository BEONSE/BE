package com.beonse2.domain.mate.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MateBoardListResponseDTO {

    private Long mbid;
    private String nickame;
    private Long paymentAmount;
    private String title;
    private String content;
    private boolean status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public MateBoardListResponseDTO(Long mbid, String nickname, Long paymentAmount, String title, String content, boolean status, Timestamp createdAt, Timestamp modifiedAt) {
        this.mbid = mbid;
        this.nickame = nickname;
        this.paymentAmount = paymentAmount;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
