package com.beonse2.domain.myPage.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.myPage.mapper.MyPageMapper;
import com.beonse2.domain.myPage.vo.MyPage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.beonse2.config.exception.ErrorCode.NOT_FOUND_INFO;
import static com.beonse2.config.exception.ErrorCode.NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final JwtUtil jwtUtil;

    private final MemberMapper memberMapper;

    private final MyPageMapper myPageMapper;

    public ResponseEntity<List<MyPage>> myInfo (String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        List<MyPage> mid = myPageMapper.findById(findMember.getMid());

        return ResponseEntity.ok(mid);
    }


}
