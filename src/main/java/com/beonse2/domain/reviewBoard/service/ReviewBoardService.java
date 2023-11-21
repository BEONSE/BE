package com.beonse2.domain.reviewBoard.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.coupon.dto.CouponResponseDTO;
import com.beonse2.domain.coupon.mapper.CouponMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.mapper.ReviewBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {

    private final ReviewBoardMapper reviewBoardMapper;
    private final MemberMapper memberMapper;
    private final CouponMapper couponMapper;
    private final BranchMapper branchMapper;
    private final JwtUtil jwtUtil;

    public SuccessMessageDTO createReviewBoard(Long couponId,
                                               ReviewBoardDTO reviewBoardDTO,
                                               String accessToken) throws IOException {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        CouponResponseDTO couponResponseDTO = couponMapper.findById(couponId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COUPON)
        );

        if (couponResponseDTO.getBranchName() == null) {
            throw new CustomException(DONT_WRITE_REVIEW);
        }

        if (couponResponseDTO.isUsed()) {
            throw new CustomException(ALREADY_WRITTEN_REVIEW);
        }
        Long branchId = branchMapper.findByBranchName(couponResponseDTO.getBranchName());

        if (reviewBoardDTO.getImage() != null && !reviewBoardDTO.getImage().isEmpty()) {
            MultipartFile image = reviewBoardDTO.getImage();

            reviewBoardDTO = ReviewBoardDTO.builder()
                    .memberMid(findMember.getMid())
                    .branchBid(branchId)
                    .couponCid(couponResponseDTO.getCid())
                    .title(reviewBoardDTO.getTitle())
                    .content(reviewBoardDTO.getContent())
                    .writer(findMember.getNickname())
                    .image(reviewBoardDTO.getImage())
                    .reviewOriginalFileName(image.getOriginalFilename())
                    .reviewImageType(image.getContentType())
                    .reviewImageData(image.getBytes())
                    .build();
        }

        reviewBoardMapper.createReviewBoard(reviewBoardDTO);

        couponResponseDTO.updateStatus(true);

        couponMapper.updateCouponWriteReview(couponResponseDTO);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("리뷰 작성")
                .build();
    }

    public PageResponseDTO findReviewBoardPage(int page, Long branchId) {

        int totalRows = reviewBoardMapper.getCount(branchId);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .paramId(branchId)
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<ReviewBoardDTO> reviewBoardDTOS = reviewBoardMapper.reviewBoardPage(pageRequestDTO);

        for (ReviewBoardDTO reviewBoardDTO : reviewBoardDTOS) {
            reviewBoardDTO.updateGrade(reviewBoardDTO.getPaymentAmount() < 150000 ? 3 : reviewBoardDTO.getPaymentAmount() < 300000 ? 2 : 1);
        }

        if (reviewBoardDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }

        return PageResponseDTO.builder()
                .content(reviewBoardDTOS)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequestDTO.getTotalPageNo())
                .build();
    }

    public SuccessMessageDTO updateReviewBoard(Long rbId, ReviewBoardDTO updatedReviewBoardDTO, String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);
        String email = jwtUtil.getEmail(token);

        // 리뷰 게시판이 사용자에게 속하는지 확인
        ReviewBoardDTO reviewBoardDTO = reviewBoardMapper.findByReviewBoardId(rbId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!email.equals(reviewBoardDTO.getWriter())) {
            throw new CustomException(NOT_MATCH_USER);
        }

        reviewBoardDTO = ReviewBoardDTO.builder()
                .title(updatedReviewBoardDTO.getTitle())
                .content(updatedReviewBoardDTO.getContent())
                .build();

        // update 메서드를 호출하여 수정된 내용을 DB에 반영
        reviewBoardMapper.updateReviewBoard(reviewBoardDTO);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("게시글 수정 성공.")
                .build();
    }

    public SuccessMessageDTO removeReviewBoard(Long rbId, String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);
        String email = jwtUtil.getEmail(token);
        System.out.println("getEmail(token)" + email);

        // 리뷰 게시판이 사용자에게 속하는지 확인
        ReviewBoardDTO reviewBoardDTO = reviewBoardMapper.findByReviewBoardId(rbId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!email.equals(reviewBoardDTO.getWriter())) {
            throw new CustomException(NOT_MATCH_USER);
        }

        reviewBoardMapper.deleteReviewBoard(rbId);
        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("게시글 삭제 완료")
                .build();
    }
}
