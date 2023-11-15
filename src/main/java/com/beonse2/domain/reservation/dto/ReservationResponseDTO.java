package com.beonse2.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ReservationResponseDTO {

    private Long memberId;

    private Long branchId;

    private String reservationTime;

    private boolean status;

    @Builder
    public ReservationResponseDTO(Long memberId, Long branchId, String reservationTime, boolean status) {
        this.memberId = memberId;
        this.branchId = branchId;
        this.reservationTime = reservationTime;
        this.status = status;
    }
}
