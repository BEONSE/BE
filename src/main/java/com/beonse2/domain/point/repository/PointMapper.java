package com.beonse2.domain.point.repository;

import com.beonse2.domain.point.vo.PointVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PointMapper {

    void save(PointVO pointVO);
    
    List<PointVO> findAllByMemberMidOrderByPaymentDateDesc(Long memberMid);
}
