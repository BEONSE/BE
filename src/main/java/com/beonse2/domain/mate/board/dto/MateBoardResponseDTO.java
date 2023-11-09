package com.beonse2.domain.mate.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MateBoardResponseDTO {

    private Long mbid;
    private Long memberId;
    private String title;
    private String content;
    private boolean status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public MateBoardResponseDTO(Long mbid, Long memberId, String title, String content, boolean status, Timestamp createdAt, Timestamp modifiedAt) {
        this.mbid = mbid;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
