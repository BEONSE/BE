package com.beonse2.domain.branch.controller;

import com.beonse2.config.service.ResponseService;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.sevice.BranchService;
import com.beonse2.domain.member.dto.TokenDTO;
import com.beonse2.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    private final MemberService memberService;

    private final ResponseService responseService;
    ResponseEntity responseEntity;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/joinbranch")
    public ResponseEntity<SuccessMessageDTO> save(@RequestBody BranchRequestDTO branchRequestDTO) {
        branchService.save(branchRequestDTO);
        System.out.println("branchRequestDTO : " + branchRequestDTO);

        TokenDTO token = memberService.tokenGenerator(branchRequestDTO.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken()); // "Bearer"를 붙여서 전송

        responseEntity = ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("성공적으로 회원가입이 완료되었습니다.")
                .build());

        return responseEntity;
    }

}
