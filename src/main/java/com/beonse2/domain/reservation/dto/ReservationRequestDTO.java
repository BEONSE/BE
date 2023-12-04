package com.beonse2.domain.reservation.dto;

import lombok.Builder;

public class ReservationRequestDTO {

    private Long bid;
    private String date;

    @Builder
    public ReservationRequestDTO(Long bid, String date) {
        this.bid = bid;
        this.date = date;
    }
}
