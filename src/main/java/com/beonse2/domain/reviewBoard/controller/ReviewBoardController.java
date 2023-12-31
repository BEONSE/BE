package com.beonse2.domain.reviewBoard.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewBoardController {

    private final ReviewBoardService reviewBoardService;

    @PostMapping("/coupons/{coupon-id}/reviews") //리뷰작성
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postReviewBoard(@PathVariable("coupon-id") Long couponId,
                                                             @RequestPart(required = false) MultipartFile image,
                                                             @RequestPart ReviewBoardDTO reviewBoardDTO,
                                                             @RequestHeader(value = "Authorization") String accessToken) throws IOException {
        return ResponseEntity.ok(reviewBoardService.createReviewBoard(couponId, image, reviewBoardDTO, accessToken));
    }

    @GetMapping("/reviews/{branch-id}") // 리뷰 전체 조회
    public ResponseEntity<PageResponseDTO> getReviewBoardPage(@RequestParam(defaultValue = "1") int page,
                                                              @PathVariable("branch-id") Long branchId) {
        return ResponseEntity.ok(reviewBoardService.findReviewBoardPage(page, branchId));
    }

    @PatchMapping("/reviews/{reviewBoard-id}") // 리뷰 업데이트
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> patchReviewBoard(@PathVariable(value = "reviewBoard-id", required = false) Long rbId,
                                                              @RequestPart MultipartFile image,
                                                              @RequestPart ReviewBoardDTO updatedReviewBoardDTO,
                                                              @RequestHeader(value = "Authorization") String accessToken) throws IOException {
        return ResponseEntity.ok(reviewBoardService.updateReviewBoard(rbId, image, updatedReviewBoardDTO, accessToken));
    }

    @PostMapping("/reviews/{reviewBoard-id}") //리뷰 삭제
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> deleteReviewBoard(@PathVariable(value = "reviewBoard-id", required = false) Long rbId,
                                                               @RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(reviewBoardService.removeReviewBoard(rbId, accessToken));
    }
}
