package com.beonse2.config.utils.page;

import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDTO {

    private List<?> content;
    private int page;			//현재 페이지 번호
    private int size;	        //그룹당 페이지 수
    private int totalRows;		//전체 행수
    private int totalPageNo;	//전체 페이지 수

    @Builder
    public PageResponseDTO(int page, int size, int totalRows, int totalPageNo, List<?> content) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalRows = totalRows;
        this.totalPageNo = totalPageNo;
    }

}
