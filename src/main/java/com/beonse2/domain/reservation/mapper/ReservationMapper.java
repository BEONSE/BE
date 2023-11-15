package com.beonse2.domain.reservation.mapper;

import com.beonse2.domain.reservation.vo.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {
    void createReservation(Reservation reservation);
}
