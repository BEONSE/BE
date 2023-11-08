package com.beonse2.domain.point.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.domain.point.dto.PointRequestDTO;
import com.beonse2.domain.point.dto.PointResponseDTO;
import com.beonse2.domain.point.mapper.PointMapper;
import com.beonse2.domain.point.vo.PointVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.beonse2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointMapper pointMapper;

    public ResponseEntity<PointResponseDTO> createPoint(PointRequestDTO pointRequestDTO) {

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

        pointMapper.save(PointVO.builder()
                .points(points)
                .paymentPrice(pointRequestDTO.getPaymentPrice())
                .cardName(pointRequestDTO.getCardName())
                .cardNumber(pointRequestDTO.getCardNumber())
                .build());

        PointVO pointVO = PointVO.builder()
                .points(points)
                .paymentPrice(pointRequestDTO.getPaymentPrice())
                .cardName(pointRequestDTO.getCardName())
                .cardNumber(pointRequestDTO.getCardNumber())
                .build();

        return ResponseEntity.ok(PointResponseDTO.builder()
                .pointVO(pointVO)
                .build());
    }

    public ResponseEntity<List<PointResponseDTO>> findPointList(Long memberMid) {

        List<PointVO> pointVOS = pointMapper.findAllByMemberMidOrderByPaymentDateDesc(memberMid);

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
