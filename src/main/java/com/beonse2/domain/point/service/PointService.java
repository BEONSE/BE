package com.beonse2.domain.point.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.exception.ErrorCode;
import com.beonse2.config.jwt.TokenProvider;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PointService {

    private final PointMapper pointMapper;
    private final TokenProvider tokenProvider;
    private final MemberMapper memberMapper;

    @Transactional
    public ResponseEntity<SuccessMessageDTO> createPoint(PointRequestDTO pointRequestDTO, String accessToken) {

        String token = tokenProvider.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(tokenProvider.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );


        if (!(pointRequestDTO.getPaymentPrice() == 10000 || pointRequestDTO.getPaymentPrice() == 30000 || pointRequestDTO.getPaymentPrice() == 50000)) {
            throw new CustomException(NOT_MATCH_PAYMENT_PRICE);
        }

        if (pointRequestDTO.getCardName().equals("") || pointRequestDTO.getCardName().equals(null)) {
            throw new CustomException(WRONG_CARD_NAME);
        }

        if (pointRequestDTO.getCardNumber().length() != 19) {
            throw new CustomException(WRONG_CARD_NUMBER);
        }

        int points = (int) (pointRequestDTO.getPaymentPrice() * 1.1);

        pointMapper.savePoint(PointVO.builder()
                .points(points)
                .memberMid(findMember.getMid())
                .paymentPrice(pointRequestDTO.getPaymentPrice())
                .cardName(pointRequestDTO.getCardName())
                .cardNumber(pointRequestDTO.getCardNumber())
                .build());


        return ResponseEntity.ok(SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("포인트 구매가 정상적으로 완료되었습니다.")
                .build());
    }

    public ResponseEntity<List<PointResponseDTO>> findPointList(String accessToken) {

        String token = tokenProvider.resolveToken(accessToken);

        MemberDTO findMember = memberMapper.findByEmail(tokenProvider.getEmail(token)).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        List<PointVO> pointVOS = pointMapper.findMyPayments(findMember.getMid());

        if (pointVOS.isEmpty()) {
            throw new CustomException(NOT_FOUND_PAYMENT);
        }

        List<PointResponseDTO> pointResponseDTOS = new ArrayList<>();
        for (PointVO pointVO : pointVOS) {
            PointResponseDTO pointResponseDTO = PointResponseDTO.builder()
                    .pointVO(pointVO)
                    .build();

            pointResponseDTOS.add(pointResponseDTO);
        }

        return ResponseEntity.ok(pointResponseDTOS);
    }
}
