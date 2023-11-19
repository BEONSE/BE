package com.beonse2.domain.branch.mapper;

import com.beonse2.domain.branch.dto.BranchDTO;
import com.beonse2.domain.branch.dto.BranchListDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.dto.ImageDTO;
import com.beonse2.domain.branch.vo.Branch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BranchMapper {
    void save(Branch branch);

    Optional<BranchRequestDTO> findByMemberId(Long memberId);

    Long findByBranchName(String branchName);

    List<String> findAllBranchName();

    List<BranchListDTO> findByAllBranch();

    List<BranchListDTO> searchBranches(String name);

    void updateBranchInfo(Branch branch);

    void saveImage(ImageDTO imageDTO);

    Optional<BranchDTO> findById(Long branchId);

    List<ImageDTO> findImage(Long branchId);
}
