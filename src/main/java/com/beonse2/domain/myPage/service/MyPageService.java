package com.beonse2.domain.myPage.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardListResponseDTO;
import com.beonse2.domain.mate.board.mapper.MateBoardMapper;
import com.beonse2.domain.mate.comment.mapper.MateCommentMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.myPage.mapper.MyPageMapper;
import com.beonse2.domain.myPage.vo.MyPage;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.mapper.ReservationMapper;
import com.beonse2.domain.reviewBoard.dto.ReviewBoardDTO;
import com.beonse2.domain.reviewBoard.mapper.ReviewBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final JwtUtil jwtUtil;
    private final MemberMapper memberMapper;
    private final MyPageMapper myPageMapper;
    private final ReviewBoardMapper reviewBoardMapper;
    private final MateBoardMapper mateBoardMapper;
    private final MateCommentMapper mateCommentMapper;
    private final ReservationMapper reservationMapper;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MyPage myInfo(String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        MyPage myPage = myPageMapper.findById(findMember.getMid()).orElseThrow(
                () -> new CustomException(NOT_FOUND_INFO)
        );

        myPage.updateGrade(myPage.getPaymentAmount() < 150000 ? 3 : myPage.getPaymentAmount() < 300000 ? 2 : 1);

        return myPage;
    }


    public PageResponseDTO findMyReviewPage(String accessToken, int page) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        int totalRows = reviewBoardMapper.getCountByMemberId(findMember.getMid());

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .paramId(findMember.getMid())
                .rowsPerPage(10)
                .pagesPerGroup(10)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<ReviewBoardDTO> reviewBoardDTOS = reviewBoardMapper.myReviewBoardPage(pageRequestDTO);

        if (reviewBoardDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }
        return PageResponseDTO.builder()
                .content(reviewBoardDTOS)
                .page(page)
                .size(10)
                .totalRows(totalRows)
                .totalPageNo(pageRequestDTO.getTotalPageNo())
                .build();

    }

    public PageResponseDTO findMyMatePage(String accessToken, int page) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        int totalRows = mateBoardMapper.getCountByMemberId(findMember.getMid());

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(findMember.getMid())
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<MateBoardListResponseDTO> mateBoards = mateBoardMapper.findMyMateBoardPage(pageRequest);

        if (mateBoards.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }

        for (MateBoardListResponseDTO mateBoard : mateBoards) {
            int commentCount = mateCommentMapper.findCommentCount(mateBoard.getMbid());

            mateBoard.updateCommentCount(commentCount);
            mateBoard.updateGrade(mateBoard.getPaymentAmount() < 150000 ? 3 : mateBoard.getPaymentAmount() < 300000 ? 2 : 1);
        }

        return PageResponseDTO.builder()
                .content(mateBoards)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build();
    }

    public PageResponseDTO findMyReservationPage(String accessToken,
                                                 int page) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        int totalRows = reservationMapper.getCountByMemberId(findMember.getMid());

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(findMember.getMid())
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<ReservationResponseDTO> reservationList = reservationMapper.findMyReservationPage(pageRequest);

        if (reservationList.isEmpty()) {
            throw new CustomException(NOT_FOUND_RESERVATION);
        }

        return PageResponseDTO.builder()
                .content(reservationList)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build();
    }

}
