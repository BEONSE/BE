package com.beonse2.domain.member.controller;

import com.beonse2.config.jwt.TokenProvider;
import com.beonse2.config.response.BaseResponse;
import com.beonse2.config.response.SingleDataResponse;
import com.beonse2.config.service.ResponseService;
import com.beonse2.domain.member.dto.LoginDTO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.dto.TokenDTO;
import com.beonse2.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;


    private final ResponseService responseService;


    private final TokenProvider tokenProvider;

    ResponseEntity responseEntity;

    //회원 가입
    @PostMapping("/join")
    public ResponseEntity<?> save(@RequestBody MemberDTO member) {
        try {
            memberService.save(member);
            System.out.println("Member : " + member);
            TokenDTO token = memberService.tokenGenerator(member.getEmail());


            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token.getAccessToken()); // "Bearer"를 붙여서 전송

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, member.getEmail(), token.getAccessToken());
            responseEntity = ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
           /* ResponseCookie responseCookie =
                    ResponseCookie.from(HttpHeaders.SET_COOKIE, token.getRefreshToken())///new Cookie("refreshToken", token.getRefreshToken());
                            .path("/")
                            .maxAge(14 * 24 * 60 * 60) // 14일
                            .httpOnly(true)
                            // .httpOnly(true).secure(true)
                            .build();
            System.out.println("responseCookie : "  + responseCookie);

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, member.getEmail(), token.getAccessToken());
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(response);*/

        } catch (RuntimeException exception) {
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
        return responseEntity;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDto) {
        try {
            String userId = memberService.login(loginDto);
            TokenDTO token = memberService.tokenGenerator(userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token.getAccessToken()); // "Bearer"를 붙여서 전송

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, userId, token.getAccessToken());
            responseEntity = ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);

           /* ResponseCookie responseCookie =
                    ResponseCookie.from(HttpHeaders.SET_COOKIE, token.getRefreshToken())///new Cookie("refreshToken", token.getRefreshToken());
                            .path("/")
                            .maxAge(14 * 24 * 60 * 60) // 14일
                            .httpOnly(true)
                            // .secure(true)
                            .build();

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, userId, token.getAccessToken());
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(response);*/

        } catch (RuntimeException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    /*@PostMapping(value = "/logout")
    public ResponseEntity logout(
            @CookieValue(value = HttpHeaders.SET_COOKIE) Cookie refreshCookie
    ) {
        try {
            ResponseCookie responseCookie =
                    ResponseCookie.from(HttpHeaders.SET_COOKIE, "")///new Cookie("refreshToken", token.getRefreshToken());
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .maxAge(0).build();
            BaseResponse response =
                    responseService.getBaseResponse(true, "로그아웃 성공");
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(response);

        } catch (RuntimeException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }*/

    /**
     * @param email email 전송을 위한 DTO
     * @return email 있다면 success값을 true, 없다면 false를 리턴.
     */
    @GetMapping(value = "/get")
    public ResponseEntity isHaveUser(@RequestParam String email) {
        // Cookie cookie = new Cookie("name", value)
        try {
            boolean isHaveUser = memberService.haveMember(email);
            String message = isHaveUser ? "회원가입된 유저입니다." : "회원가입 안된 유저입니다.";
            SingleDataResponse<Boolean> response = responseService.getSingleDataResponse(true, message, isHaveUser);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);


        } catch (RuntimeException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;

    }
}
