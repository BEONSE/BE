package com.beonse2.member.service;

import com.beonse2.config.jwt.TokenProvider;
import com.beonse2.exception.CustomException;
import com.beonse2.member.dto.LoginDTO;
import com.beonse2.member.dto.MemberDTO;
import com.beonse2.member.dto.TokenDTO;
import com.beonse2.member.mapper.MemberMapper;
import com.beonse2.member.vo.Member;
import com.beonse2.member.vo.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MemberService {

    // 암호화
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenProvider jwtTokenProvider;

    // 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    Timestamp sysDate = Timestamp.valueOf(localTime);

    private final MemberMapper memberMapper;

    /**
     * 유저 회원가입
     * @param member
     */
    @Transactional
    public boolean save(MemberDTO member) {
        // 가입된 유저인지 확인
        if (memberMapper.findByEmail(member.getEmail()).isPresent()) {
            System.out.println("이미 가입된 회원입니다");
            throw new CustomException("이미 가입된 회원입니다");
        }

        // 가입 안했으면 아래 진행

        member = MemberDTO.builder()
                .email(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .nickname(member.getNickname())
                .name(member.getName())
                .address(member.getAddress())
                .role(member.getRole())
//                .role(Role.valueOf("ROLE_USER"))
/*                .createdAt(sysDate)
                .modifiedAt(sysDate)*/
                .build();

        memberMapper.save(member);

        return memberMapper.findByEmail(member.getEmail()).isPresent();
    }

    /**
     * 토큰 발급받는 메소드
     * @param loginDTO 로그인 하는 유저의 정보
     * @return result[0]: accessToken, result[1]: refreshToken
     */
    public String login (LoginDTO loginDTO) {

        MemberDTO memberDTO = memberMapper.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomException("잘못된 아이디입니다"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), memberDTO.getPassword())) {
            throw new CustomException("잘못된 비밀번호입니다");
        }

        return memberDTO.getEmail();

    }

    /**
     * 유저가 db에 있는지 확인하는 함수
     * @param email 유저의 아이디 입력
     * @return 유저가 있다면: true, 유저가 없다면: false
     */
    public boolean haveMember(String email) {
        if (memberMapper.findByEmail(email).isPresent()) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 유저의 아이디를 찾는 함수
     * @param email 유저의 아이디 입력
     * @return 유저의 아이디가 없다면 에러를 뱉고, 있다면 userId리턴
     */
    public MemberDTO findByEmail(String email) {
        return memberMapper.findByEmail(email)
                .orElseThrow(() ->
                        new CustomException("중복된 유저 입니다."));
    }

    public TokenDTO tokenGenerator(String email) {

        MemberDTO memberDTO = memberMapper.findByEmail(email)
                .orElseThrow(() -> new CustomException("잘못된 아이디입니다"));

        return TokenDTO.builder()
                .accessToken(jwtTokenProvider.createAcessToken(memberDTO.getEmail(), Role.valueOf(memberDTO.getRole().toString())))
                .refreshToken(jwtTokenProvider.createRefreshToken(memberDTO.getEmail(), Role.valueOf(memberDTO.getRole().toString())))
                .build();
    }

}