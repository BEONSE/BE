package com.beonse2.domain.mate.board.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class MateBoardVO {

    private Long mbid;
    private Long memberId;
    private String title;
    private String content;
    private boolean status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public MateBoardVO(Long memberId, String title, String content, boolean status, Timestamp createdAt, Timestamp modifiedAt) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
