package com.beonse2.config.jwt;

import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null) {
            log.error("email을 찾을 수 없습니다.");
            throw new UsernameNotFoundException("email을 찾을 수 없습니다.");
        } else {
            log.info(email);
        }

        return memberMapper.findByEmail(email)
                .map(member -> addAuthorities(member))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private MemberDTO addAuthorities(MemberDTO memberDTO) {
       // memberDTO.setAuthorities(List.of(new SimpleGrantedAuthority(memberDTO.getRole().toString())));
        memberDTO.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(memberDTO.getRole().toString())));
        return memberDTO;
    }


}
