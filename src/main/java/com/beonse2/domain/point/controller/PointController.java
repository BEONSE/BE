package com.beonse2.domain.point.controller;

import com.beonse2.domain.point.dto.PointRequestDTO;
import com.beonse2.domain.point.dto.PointResponseDTO;
import com.beonse2.domain.point.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @Operation(summary = "포인트 결제", description = "포인트 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/payments")
    public ResponseEntity<PointResponseDTO> postPoint(@RequestBody PointRequestDTO pointRequestDTO) {
        return pointService.createPoint(pointRequestDTO);
    }

    @Operation(summary = "포인트 결제", description = "포인트 결제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping("/mypage/payments")
    public ResponseEntity<List<PointResponseDTO>> getPointList(Long memberMid) {
        return pointService.findPointList(memberMid);
    }
}
