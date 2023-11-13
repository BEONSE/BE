package com.beonse2.domain.member.mapper;

import com.beonse2.domain.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void save(MemberDTO member);

    Optional<MemberDTO> findByEmail(String email);


    Optional<Object> findNameByEmail(String email);

}

