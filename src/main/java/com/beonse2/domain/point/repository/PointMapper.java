package com.beonse2.domain.point.repository;

import com.beonse2.domain.point.vo.PointVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {

    void save(PointVO pointVO);
}
