package com.beonse2.domain.mate.board.dto;

import lombok.Getter;

@Getter
public class MateBoardUpdateRequestDTO {

    private Long mbid;
    private String title;
    private String content;

}
