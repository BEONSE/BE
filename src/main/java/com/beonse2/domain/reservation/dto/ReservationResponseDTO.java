package com.beonse2.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationResponseDTO {

    private Long rvid;

    private Long memberMid;

    private Long branchBid;

    private String branchName;

    private String reservationTime;

    private boolean status;

    @Builder
    public ReservationResponseDTO(Long rvid, Long memberMid, Long branchBid, String branchName, String reservationTime, boolean status) {
        this.rvid = rvid;
        this.memberMid = memberMid;
        this.branchBid = branchBid;
        this.branchName = branchName;
        this.reservationTime = reservationTime;
        this.status = status;
    }
}
