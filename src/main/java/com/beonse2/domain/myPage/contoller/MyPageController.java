package com.beonse2.domain.myPage.contoller;

import com.beonse2.domain.myPage.mapper.MyPageMapper;
import com.beonse2.domain.myPage.service.MyPageService;
import com.beonse2.domain.myPage.vo.MyPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping //내 정보
    public  ResponseEntity<List<MyPage>> myInfo (@RequestHeader(value = "Authorization") String accessToken) {
        return myPageService.myInfo(accessToken);
    }

    //@PatchMapping("/info") //회원정보 수정



//    @PatchMapping("/coupons/{coupon-id}") //세차 쿠폰 사용

}
