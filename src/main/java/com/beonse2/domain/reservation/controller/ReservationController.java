package com.beonse2.domain.reservation.controller;

import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.dto.ReservationTimeDTO;
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
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "세차 예약", description = "세차 예약")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!")
    })
    @PostMapping("/branches/{branch-id}/reservation")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessMessageDTO> postReservation(@PathVariable("branch-id") Long branchId,
                                                             @RequestBody ReservationResponseDTO reservationResponseDTO,
                                                             @RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.ok(reservationService.createReservation(branchId, reservationResponseDTO, accessToken));
    }

    @GetMapping("/branches/{branch-id}/reservation")
    @PreAuthorize("hasRole('BRANCH')")
    public ResponseEntity<PageResponseDTO> getReservationPage(@PathVariable("branch-id") Long branchId,
                                                              @RequestHeader(value = "Authorization") String accessToken,
                                                              @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(reservationService.findReservationPage(branchId, accessToken, page));
    }

    @GetMapping("/reservation/{branch-id}/{date}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReservationTimeDTO>> getReservationTimeList(@PathVariable("branch-id") Long branchId,
                                                                           @PathVariable("date") String date) {
        return ResponseEntity.ok(reservationService.findReservationTimeList(branchId, date));
    }

    @GetMapping("/reservation/{branch-id}")
    public ResponseEntity<String> getBranchName(@PathVariable("branch-id") Long branchId) {
        return ResponseEntity.ok(reservationService.findBranchName(branchId));
    }
}
