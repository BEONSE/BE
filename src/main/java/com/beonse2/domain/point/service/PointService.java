package com.beonse2.domain.point.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.jwt.JwtUtil;
import com.beonse2.config.utils.page.PageRequestDTO;
import com.beonse2.config.utils.page.PageResponseDTO;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import com.beonse2.domain.point.dto.PointRequestDTO;
import com.beonse2.domain.point.dto.PointResponseDTO;
import com.beonse2.domain.point.mapper.PointMapper;
import com.beonse2.domain.point.vo.PointVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PointService {

    private final PointMapper pointMapper;
    private final JwtUtil jwtUtil;
    private final MemberMapper memberMapper;

    @Transactional
    public SuccessMessageDTO createPoint(PointRequestDTO pointRequestDTO, String accessToken) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        if (!(pointRequestDTO.getPaymentPrice() == 11000 || pointRequestDTO.getPaymentPrice() == 33000 || pointRequestDTO.getPaymentPrice() == 55000)) {
            throw new CustomException(NOT_MATCH_PAYMENT_PRICE);
        }

        if (pointRequestDTO.getCardName().equals("") || pointRequestDTO.getCardName().equals(null)) {
            throw new CustomException(WRONG_CARD_NAME);
        }

        if (pointRequestDTO.getCardNumber().length() != 19) {
            throw new CustomException(WRONG_CARD_NUMBER);
        }

        int points = (int) (pointRequestDTO.getPaymentPrice() * 1.1);

        int memberPayment = findMember.getPaymentAmount() + pointRequestDTO.getPaymentPrice();

        int memberPoint = findMember.getPointAmount() + points;

        findMember.updateAmounts(memberPayment, memberPoint);

        pointMapper.savePoint(PointVO.builder()
                .points(points)
                .memberMid(findMember.getMid())
                .paymentPrice(pointRequestDTO.getPaymentPrice())
                .cardName(pointRequestDTO.getCardName())
                .cardNumber(pointRequestDTO.getCardNumber())
                .build());

        memberMapper.updatePayment(findMember);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("포인트 구매가 정상적으로 완료되었습니다.")
                .build();
    }

    public PageResponseDTO findPointList(String accessToken, int page) {

        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );
        int totalRows = pointMapper.getCount(findMember.getMid());

        PageRequestDTO pageRequest = PageRequestDTO.builder()
                .paramId(findMember.getMid())
                .rowsPerPage(5)
                .pagesPerGroup(5)
                .totalRows(totalRows)
                .page(page)
                .build();

        List<PointResponseDTO> pointResponseDTOS = pointMapper.findMyPayments(pageRequest);

        if (pointResponseDTOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_PAYMENT);
        }

        return PageResponseDTO.builder()
                .page(page)
                .size(5)
                .totalRows(totalRows)
                .totalPageNo(pageRequest.getTotalPageNo())
                .content(pointResponseDTOS)
                .build();
    }

    public Map<String, Integer> findPoint(String accessToken) {
        String token = jwtUtil.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(jwtUtil.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        Map<String, Integer> map = new HashMap<>();
        map.put("point", findMember.getPointAmount());

        return map;
    }
}
