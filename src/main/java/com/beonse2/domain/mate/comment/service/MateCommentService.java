package com.beonse2.domain.mate.comment.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public SuccessMessageDTO createMateComment(Long mateBoardId,
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

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("댓글 작성 완료.")
                .build();
    }

    public PageResponseDTO findMateCommentList(Long mateBoardId, int page) {

        int totalRows = mateCommentMapper.getCount(mateBoardId);
        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(mateBoardId)
                .rowsPerPage(6)
                .pagesPerGroup(6)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<MateCommentResponseDTO> mateComments = mateCommentMapper.findAllMateComment(pageRequest);

        if (mateComments.isEmpty()) {
            throw new CustomException(NOT_FOUND_COMMENT);
        }

        for (MateCommentResponseDTO mateComment : mateComments) {
            mateComment.updateGrade(mateComment.getPaymentAmount() < 150000 ? 3 : mateComment.getPaymentAmount() < 300000 ? 2 : 1);
        }

        return PageResponseDTO.builder()
                .page(page)
                .size(6)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .content(mateComments)
                .build();
    }

    public SuccessMessageDTO removeMateComment(Long mateBoardId, Long mateCommentId, String accessToken) {

        String token = tokenProvider.resolveToken(accessToken);

        memberMapper.findByEmail(tokenProvider.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        mateBoardMapper.findById(mateBoardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        mateCommentMapper.findById(mateCommentId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        mateCommentMapper.deleteMateComment(mateCommentId);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("댓글 삭제 완료.")
                .build();
    }
}
