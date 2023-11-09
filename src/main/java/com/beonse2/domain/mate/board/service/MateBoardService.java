package com.beonse2.domain.mate.board.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.utils.success.SuccessCode;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardListResponseDTO;
import com.beonse2.domain.mate.board.dto.MateBoardResponseDTO;
import com.beonse2.domain.mate.board.mapper.MateBoardMapper;
import com.beonse2.domain.mate.board.vo.MateBoardVO;
import com.beonse2.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateBoardService {

    private final MateBoardMapper mateBoardMapper;

    @Transactional
    public ResponseEntity<SuccessMessageDTO> createMateBoard(MemberDTO memberDTO, MateBoardRequestDTO mateBoardRequestDTO) {

        MateBoardVO mateBoardVO = MateBoardVO.builder()
                .memberId(memberDTO.getMid())
                .title(mateBoardRequestDTO.getTitle())
                .content(mateBoardRequestDTO.getContent())
                .build();

        mateBoardMapper.saveMateBoard(mateBoardVO);

        return ResponseEntity.ok(new SuccessMessageDTO(SuccessCode.SUCCESS_CREATE_BOARD));
    }

    public ResponseEntity<List<MateBoardListResponseDTO>> findBoardList() {

        List<MateBoardListResponseDTO> mateBoardResponseDTOS = mateBoardMapper.findAll();
        if (mateBoardResponseDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_BOARD);
        }

        return ResponseEntity.ok(mateBoardResponseDTOS);
    }

    public ResponseEntity<MateBoardResponseDTO> findBoard(Long mateBoardId) {

        MateBoardResponseDTO mateBoardResponseDTO = mateBoardMapper.findById(mateBoardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        return ResponseEntity.ok(mateBoardResponseDTO);
    }
}
