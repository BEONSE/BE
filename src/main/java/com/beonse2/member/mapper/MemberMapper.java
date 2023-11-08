package com.beonse2.member.mapper;

import com.beonse2.member.dto.MemberDTO;
import com.beonse2.member.dto.MemberRequest;
import com.beonse2.member.dto.MemberResponse;
import com.beonse2.member.vo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(MemberDTO member);

    Optional<MemberDTO> findByEmail(String email);

}

