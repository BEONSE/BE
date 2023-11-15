package com.beonse2.domain.mate.comment.mapper;

import com.beonse2.domain.mate.comment.dto.MateCommentResponseDTO;
import com.beonse2.domain.mate.comment.vo.MateCommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MateCommentMapper {

    void saveMateComment(MateCommentVO mateCommentVO);

    int findCommentCount(Long mateBoardId);

    List<MateCommentResponseDTO> findAllMateComment(Long mateBoardId);
}
