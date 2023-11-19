package com.beonse2.domain.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {


    void updateAcceptApproval(Long memberId);

    void updateRejectApproval(Long memberId);
}
