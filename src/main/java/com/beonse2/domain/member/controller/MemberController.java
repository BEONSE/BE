package com.beonse2.domain.member.controller;

import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.TokenProvider;
import com.beonse2.config.response.BaseResponse;
import com.beonse2.config.response.SingleDataResponse;
import com.beonse2.config.service.ResponseService;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.member.dto.LoginDTO;
import com.beonse2.domain.member.dto.LoginResponseDTO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.dto.TokenDTO;
import com.beonse2.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

import static com.beonse2.config.exception.ErrorCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;


    private final ResponseService responseService;


    private final TokenProvider tokenProvider;

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
        TokenDTO token = memberService.tokenGenerator(member.getEmail());


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken()); // "Bearer"를 붙여서 전송

        responseEntity = ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("성공적으로 회원가입이 완료되었습니다.")
                .build());

        return responseEntity;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDto) {

        String userId = memberService.login(loginDto);
        TokenDTO token = memberService.tokenGenerator(userId);

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

        return ResponseEntity.ok(LoginResponseDTO.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .statusCode(HttpStatus.OK.value())
                .successMessage("로그인 성공")
                .build());
    }

    @PostMapping(value = "/login2")
    public ResponseEntity login2(@RequestBody LoginDTO loginDto) {

        @Operation(summary = "로그인", description = "로그인")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "OK !!"),
                @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
        })
        public ResponseEntity login (@RequestBody LoginDTO loginDto){
            String userId = memberService.login(loginDto);
            TokenDTO token = memberService.tokenGenerator(userId);

            return ResponseEntity.ok(LoginResponseDTO.builder()
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .successMessage("로그인 성공")
                    .build());
            String userId = memberService.login(loginDto);
            TokenDTO token = memberService.tokenGenerator(userId);
            HttpHeaders httpHeaders = new HttpHeaders();

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
            String accessToken = token.getAccessToken();

            return ResponseEntity.ok().header("Authorization", token.getAccessToken())
                    .body(SuccessMessageDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .successMessage("")
                            .build());
        }
        return ResponseEntity.ok()
                .header("AccessToken", token.getAccessToken())
                .header("RefreshToken", token.getRefreshToken())
                .header("Origin", "http://localhost:3000")
                .body(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("")
                .build());
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
