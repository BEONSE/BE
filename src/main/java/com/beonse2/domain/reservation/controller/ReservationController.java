package com.beonse2.domain.reservation.controller;

import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/branches/{branch-id}/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "세차 예약", description = "세차 예약")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessMessageDTO> createReservation (@PathVariable("branch-id") Long branchId,
                                                                @RequestBody ReservationResponseDTO reservationResponseDTO,
                                                                @RequestHeader(value = "Authorization") String accessToken) {
        return reservationService.save(branchId, reservationResponseDTO, accessToken);
    }

    @Operation(summary = "세차 예약 조회", description = "세차 예약 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReservationResponseDTO>> reservationList(@PathVariable("branch-id") Long branchId,
                                                                        @RequestHeader(value = "Authorization") String accessToken) {
        return reservationService.reservationList(branchId, accessToken);
    }

}
