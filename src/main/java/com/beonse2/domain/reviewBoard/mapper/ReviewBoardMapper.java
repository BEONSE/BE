package com.beonse2.domain.reviewBoard.mapper;

import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.vo.ReviewBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReviewBoardMapper {

    void createReviewBoard (ReviewBoardDTO reviewBoardDTO);

    List<ReviewBoardDTO> findByReviewBoardId(Long rbId);

    List<ReviewBoardDTO> reviewBoardList(ReviewBoardDTO reviewBoardDTO);//전체 리스트 조회

    void updateReviewBoard (ReviewBoardDTO reviewBoardDTO);

    void deleteReviewBoard (Long rbId);

}
