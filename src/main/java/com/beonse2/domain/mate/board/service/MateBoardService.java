package com.beonse2.domain.mate.board.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardListResponseDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardResponseDTO;
import com.beonse2.domain.mate.board.mapper.MateBoardMapper;
import com.beonse2.domain.mate.board.vo.MateBoardVO;
import com.beonse2.domain.mate.comment.mapper.MateCommentMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MateBoardService {

    private final MemberMapper memberMapper;
    private final MateBoardMapper mateBoardMapper;
    private final MateCommentMapper mateCommentMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<SuccessMessageDTO> createMateBoard(MateBoardRequestDTO mateBoardRequestDTO,
                                                             String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        mateBoardMapper.saveMateBoard(MateBoardVO.builder()
                .memberMid(findMember.getMid())
                .title(mateBoardRequestDTO.getTitle())
                .content(mateBoardRequestDTO.getContent())
                .branchName(mateBoardRequestDTO.getBranchName())
                .build());

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("게시글이 작성되었습니다.")
                .build());
    }

    public ResponseEntity<PageResponseDTO> findMateBoardPage(int page) {

        /*int rowsPerPage, int pagesPerGroup, int totalRows, int pageNo) {*/
        int totalRows = mateBoardMapper.getCount();

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<MateBoardListResponseDTO> mateBoards = mateBoardMapper.findMateBoardPage(pageRequest);

        if (mateBoards.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }

        for (MateBoardListResponseDTO mateBoard : mateBoards) {
            int commentCount = mateCommentMapper.findCommentCount(mateBoard.getMbid());

            mateBoard.updateCommentCount(commentCount);
            mateBoard.updateGrade(mateBoard.getPaymentAmount() < 150000 ? 3 : mateBoard.getPaymentAmount() < 300000 ? 2 : 1);
        }

        return ResponseEntity.ok(PageResponseDTO.builder()
                .content(mateBoards)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build());
    }

    public ResponseEntity<MateBoardResponseDTO> findMateBoard(Long mateBoardId, String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);

        memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        MateBoardResponseDTO mateBoardResponseDTO = mateBoardMapper.findById(mateBoardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        int commentCount = mateCommentMapper.findCommentCount(mateBoardId);

        mateBoardResponseDTO.updateCommentCount(commentCount);
        mateBoardResponseDTO.updateGrade(mateBoardResponseDTO.getPaymentAmount() < 150000 ? 3 : mateBoardResponseDTO.getPaymentAmount() < 300000 ? 2 : 1);

        return ResponseEntity.ok(mateBoardResponseDTO);
    }

    @Transactional
    public ResponseEntity<SuccessMessageDTO> updateMateBoard(Long mateBoardId,
                                                             String accessToken,
                                                             MateBoardRequestDTO mateBoardRequestDTO) {

        String token = jwtUtil.resolveToken(accessToken);
        checkWriter(mateBoardId, accessToken);

        mateBoardRequestDTO = MateBoardRequestDTO.builder()
                .mbid(mateBoardId)
                .title(mateBoardRequestDTO.getTitle())
                .content(mateBoardRequestDTO.getContent())
                .branchName(mateBoardRequestDTO.getBranchName())
                .build();

        mateBoardMapper.updateMateBoard(mateBoardRequestDTO);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("게시글이 수정되었습니다.")
                .build());
    }

    @Transactional
    public ResponseEntity<SuccessMessageDTO> removeMateBoard(Long mateBoardId, String accessToken) {

        checkWriter(mateBoardId, accessToken);

        mateBoardMapper.deleteById(mateBoardId);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("게시글이 삭제되었습니다.")
                .build());
    }

    private void checkWriter(Long mateBoardId, String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        MateBoardResponseDTO findMateBoard = mateBoardMapper.findById(mateBoardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!findMateBoard.getNickname().equals(findMember.getNickname())) {
            throw new CustomException(NOT_MATCH_USER);
        }
    }
}
