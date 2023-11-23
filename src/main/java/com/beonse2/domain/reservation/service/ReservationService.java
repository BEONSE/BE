package com.beonse2.domain.reservation.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.branch.dto.BranchDTO;
import com.beonse2.domain.branch.dto.BranchRequestDTO;
import com.beonse2.domain.branch.mapper.BranchMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.reservation.dto.ReservationResponseDTO;
import com.beonse2.domain.reservation.dto.ReservationTimeDTO;
import com.beonse2.domain.reservation.mapper.ReservationMapper;
import com.beonse2.domain.reservation.vo.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.beonse2.config.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final JwtUtil jwtUtil;
    private final ReservationMapper reservationMapper;
    private final MemberMapper memberMapper;
    private final BranchMapper branchMapper;

    @Transactional
    public SuccessMessageDTO createReservation(Long branchId, ReservationResponseDTO reservationResponseDTO, String accessToken) {
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

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.CREATED.value())
                .successMessage("예약이 완료되었습니다.")
                .build();
    }

    public PageResponseDTO findReservationPage(Long branchId, String accessToken, int page) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        BranchDTO findBranch = branchMapper.findById(branchId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        if (!findMember.getMid().equals(findBranch.getMemberMid())) {
            throw new CustomException(NOT_MATCH_USER);
        }

        int totalRows = reservationMapper.getCountByBranchId(branchId);

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(branchId)
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<ReservationResponseDTO> reservationList = reservationMapper.findBranchReservationPage(pageRequest);

        if (reservationList.isEmpty()) {
            throw new CustomException(NOT_FOUND_RESERVATION);
        }

        return PageResponseDTO.builder()
                .content(reservationList)
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .build();
    }

    public List<ReservationTimeDTO> findReservationTimeList(Long branchId, String date) {

        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("branchId", branchId);
        searchMap.put("date", date.replace("-", "/"));

        return reservationMapper.findTimeList(searchMap);
    }

    public String findBranchName(Long branchId) {

        BranchDTO findBranch = branchMapper.findById(branchId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BRANCH)
        );

        return findBranch.getBranchName();
    }
}
