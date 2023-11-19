package com.beonse2.domain.reservation.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.mapper.ReservationMapper;
import com.beonse2.domain.reservation.vo.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static com.beonse2.config.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.beonse2.config.exception.ErrorCode.NOT_FOUND_RESERVATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final JwtUtil jwtUtil;

    private final ReservationMapper reservationMapper;

    private final MemberMapper memberMapper;

    private final BranchMapper branchMapper;

    @Transactional
    public ResponseEntity<SuccessMessageDTO> save(Long branchId, ReservationResponseDTO reservationResponseDTO, String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        BranchRequestDTO findBranch = branchMapper.findByMemberId(branchId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BRANCH)
        );

        Reservation reservation = Reservation.builder()
                .branchId(findBranch.getBid())
                .memberId(findMember.getMid())
                .reservationTime(Timestamp.valueOf(reservationResponseDTO.getReservationTime()))
                .build();

        reservationMapper.createReservation(reservation);

        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("예약이 완료되었습니다.")
                .build());
    }

    public ResponseEntity<List<ReservationResponseDTO>> reservationList(Long branchId,
                                                                        String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        branchMapper.findByMemberId(branchId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BRANCH)
        );

        List<ReservationResponseDTO> reservationList = reservationMapper.findMyReservations(findMember.getMid());

        if (reservationList.isEmpty()) {
            throw new CustomException(NOT_FOUND_RESERVATION);
        }

        return ResponseEntity.ok(reservationList);
    }
}
