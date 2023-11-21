package com.beonse2.domain.member.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.member.dto.*;
import com.beonse2.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입
    @Operation(summary = "회원 가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/join")
    public ResponseEntity<SuccessMessageDTO> postMember(@RequestBody MemberDTO member) {
        return ResponseEntity.ok(memberService.createMember(member));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {

        MemberDTO memberDTO = memberService.login(loginDto);

        TokenDTO token = memberService.tokenGenerator(memberDTO.getEmail());

        return ResponseEntity.ok()
                .header("AccessToken", token.getAccessToken())
                .header("RefreshToken", token.getRefreshToken())
                .body(LoginResponseDTO.builder()
                        .role(memberDTO.getRole())
                        .statusCode(HttpStatus.OK.value())
                        .successMessage("로그인 성공")
                        .build());
    }

    /**
     * @param email email 전송을 위한 DTO
     * @return email 있다면 success값을 true, 없다면 false를 리턴.
     */
    @GetMapping(value = "/join/{email}")
    public ResponseEntity<SuccessMessageDTO> isHaveUser(@PathVariable String email) {
        return ResponseEntity.ok(memberService.haveMember(email));
    }

    @GetMapping("/main")
    public ResponseEntity<MaxPaymentDTO> getMember() {
        return ResponseEntity.ok(memberService.findMaxPayment());
    }
}
