package com.beonse2.domain.member.controller;

import com.beonse2.config.response.BaseResponse;
import com.beonse2.config.response.SingleDataResponse;
import com.beonse2.config.service.ResponseService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final ResponseService responseService;
    ResponseEntity responseEntity;

    //회원 가입
    @Operation(summary = "회원 가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/join")
    public ResponseEntity<SuccessMessageDTO> save(@RequestBody MemberDTO member) {
        memberService.save(member);
        System.out.println("Member : " + member);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("성공적으로 회원가입이 완료되었습니다.")
                .build());
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDto) {

        MemberDTO memberDTO = memberService.login(loginDto);
        TokenDTO token = memberService.tokenGenerator(memberDTO.getEmail());

        return ResponseEntity.ok()
                .header("AccessToken", token.getAccessToken())
                .header("RefreshToken", token.getRefreshToken())
                .header("Origin", "http://localhost:3000")
                .body(LoginResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .successMessage("로그인 성공")
                        .nickname(memberDTO.getNickname())
                        .build());
    }

    /**
     * @param email email 전송을 위한 DTO
     * @return email 있다면 success값을 true, 없다면 false를 리턴.
     */
    @GetMapping(value = "/get")
    public ResponseEntity isHaveUser(@RequestParam String email) {
        try {
            boolean isHaveUser = memberService.haveMember(email);
            String message = isHaveUser ? "회원가입된 유저입니다." : "회원가입 안 된 유저입니다.";
            SingleDataResponse<Boolean> response = responseService.getSingleDataResponse(true, message, isHaveUser);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);


        } catch (RuntimeException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @GetMapping("/main")
    public ResponseEntity<MaxPaymentDTO> getMember() {
        return memberService.findMaxPayment();
    }
}
