package com.beonse2.domain.reviewBoard.mapper;

import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReviewBoardMapper {

    void createReviewBoard(ReviewBoardDTO reviewBoardDTO);

    Optional<ReviewBoardDTO> findByReviewBoardId(Long rbId);

    List<ReviewBoardDTO> reviewBoardPage(PageRequestDTO pageRequestDTO);//전체 리스트 조회

    List<ReviewBoardDTO> myReviewBoardPage(PageRequestDTO pageRequestDTO);//내가 쓴 리뷰 전체 조회

    void updateReviewBoard(ReviewBoardDTO reviewBoardDTO);

    void deleteReviewBoard(Long rbId);

    int getCount(Long branchId);

    int getCountByMemberId(Long mid);
}
