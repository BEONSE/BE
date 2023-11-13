package com.beonse2.domain.reviewBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewBoardRequestDTO {
    int startPage;
    int pageNum;
    int rvId;

    @Builder
    public ReviewBoardRequestDTO(int startPage, int pageNum, int rvId) {
        this.startPage = startPage;
        this.pageNum = pageNum;
        this.rvId = rvId;
    }
}
