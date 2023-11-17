package com.beonse2.domain.member.mapper;

import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.dto.MemberEditDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(MemberDTO member);

    void saveBranch(MemberDTO member);

    Optional<MemberDTO> findByEmail(String email);

    void updatePayment(int paymentAmount);

    void updateInfo(MemberEditDTO memberEditDTO);

    void updatePoint(int point);
}

