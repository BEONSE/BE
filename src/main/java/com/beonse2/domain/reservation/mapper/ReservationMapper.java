package com.beonse2.domain.reservation.mapper;

import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.vo.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {
    void createReservation(Reservation reservation);

    List<ReservationResponseDTO> findMyReservationPage(PageRequestDTO pageRequestDTO);

    List<ReservationResponseDTO> findBranchReservationPage(PageRequestDTO pageRequest);

    int getCountByMemberId(Long memberId);

    int getCountByBranchId(Long branchId);
}
