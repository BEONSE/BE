package com.beonse2.domain.mate.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MateBoardListResponseDTO {

    private Long mbid;
    private String title;
    private Timestamp modifiedAt;
    private String nickname;
    private int paymentAmount;

    @Builder
    public MateBoardListResponseDTO(Long mbid, String title, Timestamp modifiedAt, String nickname, int paymentAmount) {
        this.mbid = mbid;
        this.title = title;
        this.modifiedAt = modifiedAt;
        this.nickname = nickname;
        this.paymentAmount = paymentAmount;
    }
}
