package com.beonse2.domain.mate.board.mapper;

import com.beonse2.domain.mate.board.vo.MateBoardVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MateBoardMapper {

    void saveMateBoard(MateBoardVO mateBoardVO);
}
