package com.beonse2.domain.myPage.contoller;

import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.dto.MemberEditDTO;
import com.beonse2.domain.member.service.MemberService;
import com.beonse2.domain.member.vo.Member;
import com.beonse2.domain.myPage.mapper.MyPageMapper;
import com.beonse2.domain.myPage.service.MyPageService;
import com.beonse2.domain.myPage.vo.MyPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    private final MemberService memberService;

    @GetMapping //내 정보
    public  ResponseEntity<MyPage> myInfo (@RequestHeader(value = "Authorization") String accessToken) {
        return myPageService.myInfo(accessToken);
    }

    @PatchMapping("/info") //회원정보 수정
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<MemberEditDTO> updateInfo(@RequestBody MemberEditDTO memberEditDTO, @RequestHeader(value = "Authorization")  String accessToken) {
        return memberService.updateInfo(memberEditDTO, accessToken);
    }



//    @PatchMapping("/coupons/{coupon-id}") //세차 쿠폰 사용

}
