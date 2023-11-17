package com.beonse2.domain.member.mapper;

import com.beonse2.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(MemberDTO member);
    void saveBranch(MemberDTO member);
    Optional<MemberDTO> findByEmail(String email);

    void updatePayment(MemberDTO member);

    void updatePoint(MemberDTO member);
}

