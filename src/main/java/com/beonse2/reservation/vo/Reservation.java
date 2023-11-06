package com.beonse2.reservation.vo;

import java.security.Timestamp;

public class Reservation {

    private Long id;//id

    private Long memberId;

    private Long branchId;

    private Timestamp reservationTime; //예약 시간

    private boolean status; //예약 상태

}
