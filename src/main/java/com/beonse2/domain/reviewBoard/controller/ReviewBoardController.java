package com.beonse2.domain.reviewBoard.controller;

import com.beonse2.config.response.BaseResponse;
import com.beonse2.config.response.SingleDataResponse;
import com.beonse2.config.service.ResponseService;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewBoardController {

    ResponseService responseService;
    ReviewBoardService reviewBoardService;

    @PostMapping("/coupons/{coupon-id}/reviews") //리뷰작성
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> createReviewBoard(@PathVariable("coupon-id") Long couponId,
                                                               ReviewBoardDTO reviewBoardDTO,
                                                               @RequestHeader(value = "Authorization") String accessToken) throws IOException {
        SuccessMessageDTO success = reviewBoardService.createReviewBoard(couponId, reviewBoardDTO, accessToken);
        return ResponseEntity.ok(success);
    }


    @GetMapping("/reviews") // 리뷰 전체 조회

    public ResponseEntity<List<ReviewBoardDTO>> reviewBoardList(@RequestParam(defaultValue = "1") int startPage,
                                                                @RequestParam(defaultValue = "1") int pageNum) {
        return reviewBoardService.reviewBoardList(startPage, pageNum);
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
}
