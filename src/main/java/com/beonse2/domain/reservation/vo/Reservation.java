package com.beonse2.domain.reservation.vo;

import java.security.Timestamp;

public class Reservation {

    private Long rvid;//id

    private Long memberId;

    private Long branchId;

    private Timestamp reservationTime; //예약 시간

    private boolean status; //예약 상태

}
