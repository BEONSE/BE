package com.beonse2.domain.reservation.mapper;

import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.domain.reservation.dto.ReservationRequestDTO;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.dto.ReservationTimeDTO;
import com.beonse2.domain.reservation.vo.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {
    void createReservation(Reservation reservation);

    List<ReservationResponseDTO> findMyReservationPage(PageRequestDTO pageRequestDTO);

    List<ReservationResponseDTO> findBranchReservationPage(PageRequestDTO pageRequest);

    int getCountByMemberId(Long memberId);

    int getCountByBranchId(Long branchId);

    List<ReservationTimeDTO> findTimeList(ReservationRequestDTO reservationRequestDTO);
}
