package com.beonse2.domain.member.mapper;

import com.beonse2.domain.branch.vo.Branch;
import com.beonse2.domain.member.dto.MaxPaymentDTO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.dto.MemberEditDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(MemberDTO member);

    void saveBranch(MemberDTO member);

    Optional<MemberDTO> findByEmail(String email);

    void updatePayment(MemberDTO member);

    void updateInfo(MemberEditDTO memberEditDTO);

    void updateBranchInfo(Branch branch);

    void updatePoint(MemberDTO member);

    Optional<MemberDTO> findByNickname(String nickname);

    Optional<MemberDTO> findById(Long memberId);

    Optional<MaxPaymentDTO> findMaxPayment();

    void updatePassword(MemberDTO findMember);
}

