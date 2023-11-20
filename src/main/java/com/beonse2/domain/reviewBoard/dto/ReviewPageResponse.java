package com.beonse2.domain.reviewBoard.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewPageResponse {

    private Long branchId;
    private int page;			//현재 페이지 번호
    private int size;	        //그룹당 페이지 수
    private int totalRows;		//전체 행수
    private int totalPageNo;	//전체 페이지 수
    private List<ReviewBoardDTO> content;

    @Builder
    public ReviewPageResponse(Long branchId, int page, int size, int totalRows, int totalPageNo, List<ReviewBoardDTO> content) {
        this.branchId = branchId;
        this.page = page;
        this.size = size;
        this.totalRows = totalRows;
        this.totalPageNo = totalPageNo;
        this.content = content;
    }
}
