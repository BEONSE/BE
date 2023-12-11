package com.beonse2.domain.mate.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MateBoardRequestDTO {

    private Long mbid;
    private String title;
    private String branchName;
    private String content;

    @Builder
    public MateBoardRequestDTO(Long mbid, String title, String branchName, String content) {
        this.mbid = mbid;
        this.title = title;
        this.branchName = branchName;
        this.content = content;
    }
}
