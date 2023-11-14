package com.beonse2.domain.mate.board.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.service.MateBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mates")
public class MateBoardController {

    private final MateBoardService mateBoardService;

    @Operation(summary = "포인트 결제", description = "포인트 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postMateBoard(@RequestBody MateBoardRequestDTO mateBoardRequestDTO,
                                                           @RequestHeader(value = "Authorization") String accessToken) {
        return mateBoardService.createMateBoard(mateBoardRequestDTO, accessToken);
    }
}
