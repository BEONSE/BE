package com.beonse2.domain.mate.board.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardListResponseDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardResponseDTO;
import com.beonse2.domain.mate.board.mapper.MateBoardMapper;
import com.beonse2.domain.mate.board.vo.MateBoardVO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.beonse2.config.exception.ErrorCode.*;
import static com.beonse2.config.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MateBoardService {

    private final MemberMapper memberMapper;
    private final MateBoardMapper mateBoardMapper;
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

    public ResponseEntity<List<MateBoardListResponseDTO>> findAllMateBoard() {

        List<MateBoardListResponseDTO> mateBoards = mateBoardMapper.findAllMateBoard();

        if (mateBoards.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }

        return ResponseEntity.ok(mateBoards);
    }

    public ResponseEntity<MateBoardResponseDTO> findMateBoard(Long mateBoardId, String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);

        memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        MateBoardResponseDTO mateBoardResponseDTO = mateBoardMapper.findById(mateBoardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

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
