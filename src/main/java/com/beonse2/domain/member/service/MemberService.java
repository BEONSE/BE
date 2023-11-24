package com.beonse2.domain.member.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.member.dto.*;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.member.vo.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final BranchMapper branchMapper;

    /**
     * 유저 회원가입
     *
     * @param member
     */
    @Transactional
    public SuccessMessageDTO createMember(MemberDTO member) {

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

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("성공적으로 회원가입이 완료되었습니다.")
                .build();
    }

    /**
     * 토큰 발급받는 메소드
     *
     * @param loginDTO 로그인 하는 유저의 정보
     * @return result[0]: accessToken, result[1]: refreshToken
     */
    public MemberDTO login(LoginDTO loginDTO) {

        MemberDTO memberDTO = memberMapper.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new CustomException(NOT_MATCH_MEMBER)
        );

        if (!passwordEncoder.matches(loginDTO.getPassword(), memberDTO.getPassword())) {
            throw new CustomException(NOT_MATCH_MEMBER);
        }

        if (memberDTO.getRole().equals(Role.ROLE_BRANCH)) {
            BranchRequestDTO findBranch = branchMapper.findByMemberId(memberDTO.getMid()).orElseThrow(
                    () -> new CustomException(NOT_FOUND_BRANCH)
            );
            if (memberDTO.getIsApproval().equals("승인 대기")) {
                throw new CustomException(WAITING_JOIN);
            } else if (memberDTO.getIsApproval().equals("가입 거절")) {
                throw new CustomException(REJECT_JOIN);
            }

            return MemberDTO.builder()
                    .mid(memberDTO.getMid())
                    .branchId(findBranch.getBid())
                    .role(memberDTO.getRole())
                    .email(memberDTO.getEmail())
                    .build();
        } else {
            return MemberDTO.builder()
                    .mid(memberDTO.getMid())
                    .role(memberDTO.getRole())
                    .nickname(memberDTO.getNickname())
                    .email(memberDTO.getEmail())
                    .build();
        }

    }

    /**
     * 유저가 db에 있는지 확인하는 함수
     *
     * @param email 유저의 아이디 입력
     * @return 유저가 있다면: true, 유저가 없다면: false
     */
    public SuccessMessageDTO haveMember(String email) {
        if (memberMapper.findByEmail(email).isPresent()) {
            throw new CustomException(DUPLICATE_MEMBER);
        }
        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("중복 확인 완료.")
                .build();
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

    public SuccessMessageDTO updateInfo(MultipartFile image,
                                        MemberEditDTO memberEditDTO,
                                        String accessToken) throws IOException {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        if (!(jwtUtil.getEmail(token).equals(findMember.getEmail())) && memberMapper.findByNickname(memberEditDTO.getNickname()).isPresent()) {
            throw new CustomException(DUPLICATE_NICKNAME);
        }

        Long mid = findMember.getMid();
        String email = findMember.getEmail();
        String password;
        if (memberEditDTO.getPassword() != null) {
            password = memberEditDTO.getPassword();
        } else {
            password = passwordEncoder.encode(findMember.getPassword());
        }

        if (email.equals(memberEditDTO.getEmail())) {
            if (image != null && !image.isEmpty()) {
                memberEditDTO = MemberEditDTO.builder()
                        .mid(mid)
                        .email(email)
                        .nickname(memberEditDTO.getNickname())
                        .address(memberEditDTO.getAddress())
                        .password(password)
                        .originalFileName(image.getOriginalFilename())
                        .imageType(image.getContentType())
                        .imageData(image.getBytes())
                        .modifiedAt(findMember.getModifiedAt())
                        .build();
            } else {
                memberEditDTO = MemberEditDTO.builder()
                        .mid(mid)
                        .email(email)
                        .nickname(memberEditDTO.getNickname())
                        .address(memberEditDTO.getAddress())
                        .password(password)
                        .modifiedAt(findMember.getModifiedAt())
                        .build();
            }
        } else {
            throw new CustomException(NOT_MATCH_USER);
        }

        memberMapper.updateInfo(memberEditDTO);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("회원 정보 수정 완료")
                .build();
    }

    public MaxPaymentDTO findMaxPayment() {
        return memberMapper.findMaxPayment().orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );
    }
}
