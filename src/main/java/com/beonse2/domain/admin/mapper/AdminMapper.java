package com.beonse2.domain.admin.mapper;

import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.domain.admin.dto.AllMemberDTO;
import com.beonse2.domain.admin.dto.BranchMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {


    void updateAcceptApproval(Long memberId);

    void updateRejectApproval(Long memberId);

    List<BranchMemberDTO> findAllBranchMember(PageRequestDTO pageRequest);

    int getWaitingCount();

    List<BranchMemberDTO> findResultMember(PageRequestDTO pageRequest);

    int getResultCount();

    int getMemberCount();

    List<AllMemberDTO> findAllMember(PageRequestDTO pageRequest);
}
