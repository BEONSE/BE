package com.beonse2.domain.mate.comment.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.mapper.MateBoardMapper;
import com.beonse2.domain.mate.comment.dto.MateCommentRequestDTO;
import com.beonse2.domain.mate.comment.dto.MateCommentResponseDTO;
import com.beonse2.domain.mate.comment.mapper.MateCommentMapper;
import com.beonse2.domain.mate.comment.vo.MateCommentVO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateCommentService {

    private final MemberMapper memberMapper;
    private final MateCommentMapper mateCommentMapper;
    private final MateBoardMapper mateBoardMapper;
    private final JwtUtil tokenProvider;

    @Transactional
    public ResponseEntity<SuccessMessageDTO> createMateComment(Long mateBoardId,
                                                               String accessToken,
                                                               MateCommentRequestDTO mateCommentRequestDTO) {

        String token = tokenProvider.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(tokenProvider.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        MateCommentVO mateCommentVO = MateCommentVO.builder()
                .memberMid(findMember.getMid())
                .mateBoardMbid(mateBoardId)
                .content(mateCommentRequestDTO.getContent())
                .build();

        mateCommentMapper.saveMateComment(mateCommentVO);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("댓글 작성 완료.")
                .build());
    }

    public ResponseEntity<List<MateCommentResponseDTO>> findMateCommentList(Long mateBoardId) {

        List<MateCommentResponseDTO> mateComments = mateCommentMapper.findAllMateComment(mateBoardId);

        if (mateComments.isEmpty()) {
            throw new CustomException(NOT_FOUND_COMMENT);
        }

        return ResponseEntity.ok(mateComments);
    }

    public ResponseEntity<SuccessMessageDTO> removeMateComment(Long mateBoardId, Long mateCommentId, String accessToken) {

        String token = tokenProvider.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(tokenProvider.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        mateBoardMapper.findById(mateBoardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        mateCommentMapper.findById(mateCommentId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        mateCommentMapper.deleteMateComment(mateCommentId);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("댓글 삭제 완료.")
                .build());
    }
}