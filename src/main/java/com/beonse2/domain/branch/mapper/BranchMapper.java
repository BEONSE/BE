package com.beonse2.domain.branch.mapper;

import com.beonse2.domain.branch.dto.BranchDTO;
import com.beonse2.domain.branch.vo.Branch;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchMapper {
    void save(Branch branch);
}
