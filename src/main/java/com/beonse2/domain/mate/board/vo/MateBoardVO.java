package com.beonse2.domain.mate.board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static lombok.AccessLevel.PROTECTED;

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
    public MateBoardVO(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }
}
