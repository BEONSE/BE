package com.beonse2.domain.point.mapper;

import com.beonse2.domain.point.vo.PointVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointMapper {

    void save(PointVO pointVO);
    
    List<PointVO> findAllByMemberMidOrderByPaymentDateDesc(Long memberMid);
}
