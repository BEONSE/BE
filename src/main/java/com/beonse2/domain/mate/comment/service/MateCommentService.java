package com.beonse2.domain.mate.comment.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.comment.dto.MateCommentRequestDTO;
import com.beonse2.domain.mate.comment.mapper.MateCommentMapper;
import com.beonse2.domain.mate.comment.vo.MateCommentVO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateCommentService {

    private final MemberMapper memberMapper;
    private final MateCommentMapper mateCommentMapper;
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
}
