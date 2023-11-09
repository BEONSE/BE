package com.beonse2.domain.mate.board.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.service.MateBoardService;
import com.beonse2.domain.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mates")
public class MateBoardController {

    private final MateBoardService mateBoardService;

    @Operation(summary = "쿠폰 결제", description = "쿠폰 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping
    public ResponseEntity<SuccessMessageDTO> postMateBoard(@AuthenticationPrincipal MemberDTO memberDTO,
                                                           @RequestBody MateBoardRequestDTO mateBoardRequestDTO) {
        return mateBoardService.createMateBoard(memberDTO, mateBoardRequestDTO);
    }
}
