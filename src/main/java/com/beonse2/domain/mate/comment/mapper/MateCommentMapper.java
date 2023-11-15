package com.beonse2.domain.mate.comment.mapper;

import com.beonse2.domain.mate.comment.vo.MateCommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MateCommentMapper {

    void saveMateComment(MateCommentVO mateCommentVO);

    int findCommentCount(Long mateBoardId);

}
