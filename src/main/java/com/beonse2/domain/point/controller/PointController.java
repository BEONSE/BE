package com.beonse2.domain.point.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.point.dto.PointRequestDTO;
import com.beonse2.domain.point.dto.PointResponseDTO;
import com.beonse2.domain.point.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PointController {

    private final PointService pointService;

    @Operation(summary = "포인트 결제", description = "포인트 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/payments")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postPoint(@RequestBody PointRequestDTO pointRequestDTO,
                                                       @RequestHeader(value = "Authorization") String accessToken) {
        return pointService.createPoint(pointRequestDTO, accessToken);
    }

    @Operation(summary = "포인트 결제", description = "포인트 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/mypage/payments")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<PointResponseDTO>> getPointList(@RequestHeader(value = "Authorization") String accessToken) {
        return pointService.findPointList(accessToken);
    }

    @GetMapping("/points")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Map<String, Integer>> getPointAmount(@RequestHeader("Authorization") String accessToken) {
        return pointService.findPoint(accessToken);
    }
}
