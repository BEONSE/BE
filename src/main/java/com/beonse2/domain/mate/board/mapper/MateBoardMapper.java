package com.beonse2.domain.mate.board.mapper;

import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardListResponseDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardResponseDTO;
import com.beonse2.domain.mate.board.vo.MateBoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MateBoardMapper {
    void saveMateBoard(MateBoardVO mateBoardVO);

    List<MateBoardListResponseDTO> findAllMateBoard(PageRequestDTO pageRequest);

    Optional<MateBoardResponseDTO> findById(Long mateBoardId);

    void updateMateBoard(MateBoardRequestDTO mateBoardRequestDTO);

    void deleteById(Long mateBoardId);

    int getCount();

}
