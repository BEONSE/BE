package com.beonse2.domain.mate.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MateBoardResponseDTO {

    private Long mbid;
    private String profileImage;
    private String nickname;
    private String paymentAmount;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public MateBoardResponseDTO(Long mbid, String profileImage, String nickname, String paymentAmount, String title, String content, Timestamp createdAt, Timestamp modifiedAt) {
        this.mbid = mbid;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.paymentAmount = paymentAmount;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
