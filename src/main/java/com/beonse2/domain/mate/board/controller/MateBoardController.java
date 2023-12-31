package com.beonse2.domain.mate.board.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.mate.board.dto.MateBoardRequestDTO;
import com.beonse2.domain.mate.board.dto.MateBoardResponseDTO;
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

    @Operation(summary = "메이트 게시글 등록", description = "메이트 게시글 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postMateBoard(@RequestBody MateBoardRequestDTO mateBoardRequestDTO,
                                                           @RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(mateBoardService.createMateBoard(mateBoardRequestDTO, accessToken));
    }

    @Operation(summary = "메이트 게시글 전체 조회", description = "메이트 게시글 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping
    public ResponseEntity<PageResponseDTO> getMateBoardPage(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(mateBoardService.findMateBoardPage(page));
    }

    @Operation(summary = "메이트 게시글 단건 조회", description = "메이트 게시글 단건 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/{mateBoard-id}")
    public ResponseEntity<MateBoardResponseDTO> getMateBoard(@PathVariable("mateBoard-id") Long mateBoardId) {
        return ResponseEntity.ok(mateBoardService.findMateBoard(mateBoardId));
    }

    @Operation(summary = "메이트 게시글 수정", description = "메이트 게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PatchMapping("/{mateBoard-id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> patchMateBoard(@PathVariable("mateBoard-id") Long mateBoardId,
                                                            @RequestHeader(value = "Authorization") String accessToken,
                                                            @RequestBody MateBoardRequestDTO mateBoardRequestDTO) {
        return ResponseEntity.ok(mateBoardService.updateMateBoard(mateBoardId, accessToken, mateBoardRequestDTO));
    }

    @Operation(summary = "메이트 게시글 수정", description = "메이트 게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PatchMapping("/remove/{mateBoard-id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> deleteMateBoard(@PathVariable("mateBoard-id") Long mateBoardId,
                                                             @RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(mateBoardService.removeMateBoard(mateBoardId, accessToken));
    }
}
