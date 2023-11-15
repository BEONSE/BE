package com.beonse2.domain.mate.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MateBoardResponseDTO {

    private Long mbid;
    private String title;
    private String content;
    private String nickname;
    private String paymentAmount;
    private String createdAt;
    private String modifiedAt;

    @Builder
    public MateBoardResponseDTO(Long mbid, String title, String content, String nickname, String paymentAmount, String createdAt, String modifiedAt) {
        this.mbid = mbid;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.paymentAmount = paymentAmount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
