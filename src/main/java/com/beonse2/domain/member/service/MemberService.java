package com.beonse2.domain.member.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.domain.member.dto.*;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.beonse2.config.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // 암호화
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final JwtUtil jwtUtil;

    private final MemberMapper memberMapper;

    /**
     * 유저 회원가입
     *
     * @param member
     */
    @Transactional
    public boolean save(MemberDTO member) {

        // 가입된 유저인지 확인
        if (memberMapper.findByEmail(member.getEmail()).isPresent()) {
            System.out.println("이미 가입된 회원입니다");
            throw new CustomException(ErrorCode.DUPLICATE_MEMBER);
        }

        if (memberMapper.findByNickname(member.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        // 가입 안했으면 아래 진행

        member = MemberDTO.builder()
                .email(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .nickname(member.getNickname())
                .name(member.getName())
                .address(member.getAddress())
                .role(member.getRole())
                .build();

        memberMapper.save(member);

        return memberMapper.findByEmail(member.getEmail()).isPresent();
    }

    /**
     * 토큰 발급받는 메소드
     *
     * @param loginDTO 로그인 하는 유저의 정보
     * @return result[0]: accessToken, result[1]: refreshToken
     */
    public MemberDTO login(LoginDTO loginDTO) {

        MemberDTO memberDTO = memberMapper.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_MATCH_EMAIL));

        if (!passwordEncoder.matches(loginDTO.getPassword(), memberDTO.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        return memberDTO;

    }

    /**
     * 유저가 db에 있는지 확인하는 함수
     *
     * @param email 유저의 아이디 입력
     * @return 유저가 있다면: true, 유저가 없다면: false
     */
    public boolean haveMember(String email) {
        if (memberMapper.findByEmail(email).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 유저의 아이디를 찾는 함수
     *
     * @param email 유저의 아이디 입력
     * @return 유저의 아이디가 없다면 에러를 뱉고, 있다면 userId리턴
     */
    public MemberDTO findByEmail(String email) {
        return memberMapper.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("중복된 유저 입니다."));
    }

    public TokenDTO tokenGenerator(String email) {

        MemberDTO memberDTO = memberMapper.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("잘못된 아이디입니다"));

        return TokenDTO.builder()
                .accessToken(jwtUtil.createAccessToken(memberDTO))
                .refreshToken(jwtUtil.createRefreshToken(memberDTO))
                .build();
    }

    public ResponseEntity<MemberEditDTO> updateInfo(MemberEditDTO memberEditDTO, String accessToken) throws IOException {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        if (memberMapper.findByNickname(memberEditDTO.getNickname()).isPresent()) {
            throw new CustomException(DUPLICATE_NICKNAME);
        }

        Long mid = findMember.getMid();
        String email = findMember.getEmail();
        String password;
        if (memberEditDTO.getPassword() != null) {
            password = passwordEncoder.encode(memberEditDTO.getPassword());

        } else {
            password = findMember.getPassword();
        }

        if (email.equals(memberEditDTO.getEmail())) {
            if (memberEditDTO.getImage() != null && !memberEditDTO.getImage().isEmpty()) {
                MultipartFile image = memberEditDTO.getImage();

                memberEditDTO = MemberEditDTO.builder()
                        .mid(mid)
                        .email(email)
                        .nickname(memberEditDTO.getNickname())
                        .address(memberEditDTO.getAddress())
                        .password(password)
                        .image(memberEditDTO.getImage())
                        .originalFileName(image.getOriginalFilename())
                        .imageType(image.getContentType())
                        .imageData(image.getBytes())
                        .modifiedAt(findMember.getModifiedAt())
                        .build();
            }
        } else {
            throw new CustomException(NOT_MATCH_USER);
        }

        memberMapper.updateInfo(memberEditDTO);

        return ResponseEntity.ok(memberEditDTO);
    }

    public ResponseEntity<MaxPaymentDTO> findMaxPayment() {

        MaxPaymentDTO maxPayment = memberMapper.findMaxPayment().orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        return ResponseEntity.ok(maxPayment);
    }
}
