package com.beonse2.domain.member.service;

import com.beonse2.exception.CustomException;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberMapper.findByEmail(email)
                .map(this::addAuthorities) // .map(member -> addAuthorities(member))
                .orElseThrow(() -> new CustomException(email + "email을 찾을 수 없습니다."));
    }

    private MemberDTO addAuthorities(MemberDTO memberDTO) {
        memberDTO.setAuthorities(List.of(new SimpleGrantedAuthority(memberDTO.getRole().toString())));
        //memberDTO.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(memberDTO.getRole().toString())));
        return memberDTO;
        }
    }
