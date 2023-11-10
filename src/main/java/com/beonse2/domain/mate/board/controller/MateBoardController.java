package com.beonse2.domain.mate.board.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardListResponseDTO;
import com.beonse2.domain.mate.board.dto.MateBoardResponseDTO;
import com.beonse2.domain.mate.board.dto.MateBoardUpdateRequestDTO;
import com.beonse2.domain.mate.board.service.MateBoardService;
import com.beonse2.domain.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mates")
public class MateBoardController {

    private final MateBoardService mateBoardService;

    @Operation(summary = "메이트 게시글 작성", description = "메이트 게시글 작성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping
    public ResponseEntity<SuccessMessageDTO> postMateBoard(@AuthenticationPrincipal MemberDTO memberDTO,
                                                           @RequestBody MateBoardRequestDTO mateBoardRequestDTO) {
        return mateBoardService.createMateBoard(memberDTO, mateBoardRequestDTO);
    }

    @Operation(summary = "메이트 게시글 전체 조회", description = "메이트 게시글 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping
    public ResponseEntity<List<MateBoardListResponseDTO>> getMateBoardList() {
        return mateBoardService.findBoardList();
    }

    @Operation(summary = "메이트 게시글 단건 조회", description = "메이트 게시글 단건 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/{mateBoard-id}")
    public ResponseEntity<MateBoardResponseDTO> getMateBoard(@PathVariable("mateBoard-id") Long mateBoardId) {
        return mateBoardService.findMateBoard(mateBoardId);
    }

    @Operation(summary = "메이트 게시글 수정", description = "메이트 게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PatchMapping("/{mateBoard-id}")
    public ResponseEntity<SuccessMessageDTO> patchMateBoard(@PathVariable("mateBoard-id") Long mateBoardId,
                                                            @RequestBody MateBoardUpdateRequestDTO mateBoardUpdateRequestDTO) {
        return mateBoardService.updateMateBoard(mateBoardId, mateBoardUpdateRequestDTO);
    }

    @Operation(summary = "메이트 게시글 수정", description = "메이트 게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PatchMapping("/remove/{mateBoard-id}")
    public ResponseEntity<SuccessMessageDTO> deleteMateBoard(@PathVariable("mateBoard-id") Long mateBoardId) {
        return mateBoardService.removeMateBoard(mateBoardId);
    }

}
