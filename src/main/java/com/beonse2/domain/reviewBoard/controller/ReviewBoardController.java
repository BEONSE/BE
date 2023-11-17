package com.beonse2.domain.reviewBoard.controller;

import com.beonse2.config.response.BaseResponse;
import com.beonse2.config.response.SingleDataResponse;
import com.beonse2.config.service.ResponseService;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardRequestDTO;
import com.beonse2.domain.reviewBoard.service.ReviewBoardService;
import com.beonse2.domain.reviewBoard.vo.ReviewBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewBoardController {

    @Autowired
    ResponseService responseService;

    @Autowired
    ReviewBoardService reviewBoardService;


    @PostMapping("/coupons/{coupon-id}/reviews") //리뷰작성
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> createReviewBoard(@PathVariable("coupon-id") Long couponId,
                                                               @RequestBody ReviewBoardDTO reviewBoardDTO,
                                                               @RequestHeader(value = "Authorization") String accessToken) {
        SuccessMessageDTO success = reviewBoardService.createReviewBoard(couponId, reviewBoardDTO, accessToken);
        return ResponseEntity.ok(success);
    }


    @GetMapping("/reviews") // 리뷰 전체 조회
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity reviewBoardList(@RequestParam(defaultValue = "1") int startPage, @RequestParam(defaultValue = "1") int pageNum, ReviewBoardDTO reviewBoardDTO) {

        try {
            ReviewBoardRequestDTO reviewBoardRequestDTO = ReviewBoardRequestDTO.builder().startPage(startPage).pageNum(pageNum).build();
            //   System.out.println("reviewBoardRequestDTO : " + reviewBoardRequestDTO);

            List<ReviewBoardDTO> reviewBoard = reviewBoardService.reviewBoardList(reviewBoardDTO);
            //    System.out.println("reviewBoard : " + reviewBoard);

            SingleDataResponse<List<ReviewBoardDTO>> response = responseService.getSingleDataResponse(true, "게시판 생성 성공", reviewBoard);
            //   System.out.println("response : " + response);

            // ResponseEntity를 생성하여 반환
            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            // 에러 발생 시 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        } catch (Exception e) {
            log.error("게시판 조회 실패", e);
            // 에러 발생 시 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/reviews/{reviewBoard-id}") //리뷰 단일 조회
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity findByReviewBoardId(@PathVariable(value = "reviewBoard-id", required = false) Long rbId) {
        ResponseEntity responseEntity = null;
        try {
            List<ReviewBoardDTO> reviewBoardDTO = reviewBoardService.findMyReviewBoardId(rbId);
            //System.out.println("reviewBoardDTO : " + reviewBoardDTO);

            SingleDataResponse<List<ReviewBoardDTO>> response = responseService.getSingleDataResponse(true, "단일 게시물 불러오기 성공", reviewBoardDTO);
            //System.out.println("response : " + response);

            responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            log.error("단일 조회 실패", e);
            BaseResponse response = responseService.getBaseResponse(false, "단일 게시판 불러오기 실패");
            responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
        return responseEntity;
    }

    @PatchMapping("/reviews/{reviewBoard-id}") // 리뷰 업데이트
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity updateReviewBoard(@PathVariable(value = "reviewBoard-id", required = false) Long rbId,
                                            @RequestBody ReviewBoardDTO updatedReviewBoardDTO,
                                            @RequestHeader(value = "Authorization") String accessToken) {
        ResponseEntity responseEntity = null;

        try {
            List<ReviewBoardDTO> reviewBoard = reviewBoardService.updateReviewBoard(rbId, updatedReviewBoardDTO, accessToken);
            //System.out.println("reviewBoard : " + reviewBoard);

            SingleDataResponse<List<ReviewBoardDTO>> response = responseService.getSingleDataResponse(true, "게시판 업데이트 성공", reviewBoard);
            //System.out.println("response1 : " + response);

            responseEntity = ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error("게시판 업데이트 실패", e);
            BaseResponse response = responseService.getBaseResponse(false, "게시판 업데이트 실패");
            System.out.println("response2 : " + response);
            responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }

        return responseEntity;
    }


    @PostMapping("/reviews/{reviewBoard-id}") //리뷰 삭제
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity deleteReviewBoard(@PathVariable(value = "reviewBoard-id", required = false) Long rbId,
                                            @RequestBody ReviewBoardDTO deletedReviewBoardDTO,
                                            @RequestHeader(value = "Authorization") String accessToken) {
        ResponseEntity responseEntity = null;

        try {
            reviewBoardService.deleteReviewBoard(rbId, deletedReviewBoardDTO, accessToken);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        } catch (AccessDeniedException e) {
            log.error("게시판 삭제 실패", e);
            BaseResponse response = responseService.getBaseResponse(false, "게시판 삭제 실패");
            System.out.println("response2 : " + response);
            responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }

        return responseEntity;
    }
        /*ResponseEntity responseEntity = null;

        try {
            List<ReviewBoardDTO> reviewBoard = reviewBoardService.deleteReviewBoard(rbId, updatedReviewBoardDTO, accessToken);
            System.out.println("reviewBoard : " + reviewBoard);

            SingleDataResponse<List<ReviewBoardDTO>> response = responseService.getSingleDataResponse(true, "게시판 삭제 성공", reviewBoard);
            System.out.println("response1 : " + response);

            responseEntity = ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error("게시판 삭제 실패", e);
            BaseResponse response = responseService.getBaseResponse(false, "게시판 삭제 실패");
            System.out.println("response2 : " + response);
            responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }

        return responseEntity;

    }

}*/
}
