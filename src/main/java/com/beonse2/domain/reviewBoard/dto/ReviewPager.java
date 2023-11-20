package com.beonse2.domain.reviewBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewPager {

	private Long branchId;
	private int totalRows;		//전체 행수
	private int totalPageNo;	//전체 페이지 수
	private int totalGroupNo;	//전체 그룹 수
	private int startPageNo;	//그룹의 시작 페이스 번호
	private int endPageNo;		//그룹의 끝 페이지 번호
	private int page;			//현재 페이지 번호
	private int pagesPerGroup;	//그룹당 페이지 수
	private int groupNo;		//현재 그룹 번호
	private int rowsPerPage;	//페이지당 행 수 
	private int startRowNo;		//페이지의 시작 행 번호(1, ..., n)
	private int startRowIndex;	//페이지의 시작 행 인덱스(0, ..., n-1) for mysql
	private int endRowNo;		//페이지의 마지막 행 번호
	private int endRowIndex;	//페이지의 마지막 행 인덱스

	@Builder
	public ReviewPager(Long branchId, int rowsPerPage, int pagesPerGroup, int totalRows, int page) {
		this.branchId = branchId;
		this.rowsPerPage = rowsPerPage;
		this.pagesPerGroup = pagesPerGroup;
		this.totalRows = totalRows;
		this.page = page;

		totalPageNo = totalRows / rowsPerPage;
		if(totalRows % rowsPerPage != 0) totalPageNo++;
		
		totalGroupNo = totalPageNo / pagesPerGroup;
		if(totalPageNo % pagesPerGroup != 0) totalGroupNo++;
		
		groupNo = (page - 1) / pagesPerGroup + 1;
		
		startPageNo = (groupNo-1) * pagesPerGroup + 1;
		
		endPageNo = startPageNo + pagesPerGroup - 1;
		if(groupNo == totalGroupNo) endPageNo = totalPageNo;
		
		startRowNo = (page - 1) * rowsPerPage + 1;
		startRowIndex = startRowNo - 1;
		endRowNo = page * rowsPerPage;
		endRowIndex = endRowNo - 1; 
	}
}







