package com.beonse2.domain.mate.board.service;

import com.beonse2.config.utils.success.SuccessCode;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.mapper.MateBoardMapper;
import com.beonse2.domain.mate.board.vo.MateBoardVO;
import com.beonse2.domain.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
