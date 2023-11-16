package com.beonse2.domain.reservation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class Reservation {

    private Long rvid;//id

    private Long memberId;

    private Long branchId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp reservationTime; //예약 시간

    private boolean status; //예약 상태

    @Builder
    public Reservation(Long memberId, Long branchId, Timestamp reservationTime, boolean status) {
        this.memberId = memberId;
        this.branchId = branchId;
        this.reservationTime = reservationTime;
        this.status = status;
    }
}
