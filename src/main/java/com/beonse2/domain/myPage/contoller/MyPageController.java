package com.beonse2.domain.myPage.contoller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.member.dto.MemberEditDTO;
import com.beonse2.domain.member.service.MemberService;
import com.beonse2.domain.myPage.service.MyPageService;
import com.beonse2.domain.myPage.vo.MyPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberService memberService;

    @GetMapping //내 정보
    public ResponseEntity<MyPage> myInfo(@RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(myPageService.myInfo(accessToken));
    }

    @PatchMapping("/info") //회원정보 수정
    public ResponseEntity<SuccessMessageDTO> updateInfo(@RequestPart(required = false) MultipartFile image,
                                                        @RequestPart MemberEditDTO memberEditDTO,
                                                        @RequestHeader(value = "Authorization") String accessToken) throws IOException {
        return ResponseEntity.ok(memberService.updateInfo(image, memberEditDTO, accessToken));
    }

    @GetMapping("/reviews")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<PageResponseDTO> getMyReviewPage(@RequestHeader(value = "Authorization") String accessToken,
                                                           @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(myPageService.findMyReviewPage(accessToken, page));
    }

    @GetMapping("/mates")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<PageResponseDTO> getMyMatePage(@RequestHeader(value = "Authorization") String accessToken,
                                                         @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(myPageService.findMyMatePage(accessToken, page));
    }

    @Operation(summary = "세차 예약 조회", description = "세차 예약 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/reservation")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PageResponseDTO> getReservationPage(@RequestHeader(value = "Authorization") String accessToken,
                                                              @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(myPageService.findMyReservationPage(accessToken, page));
    }

}
