package com.beonse2.domain.mate.board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MateBoardVO {

    private Long mbid;
    private Long memberMid;
    private String title;
    private String content;
    private boolean status;
    private String branchName;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @Builder
    public MateBoardVO(Long memberMid, String title, String content, boolean status, String branchName) {
        this.memberMid = memberMid;
        this.title = title;
        this.content = content;
        this.branchName = branchName;
    }
}
