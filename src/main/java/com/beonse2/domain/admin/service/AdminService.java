package com.beonse2.domain.admin.service;

import com.beonse2.config.exception.CustomException;
import com.beonse2.config.utils.success.SuccessMessageDTO;
import com.beonse2.domain.admin.mapper.AdminMapper;
import com.beonse2.domain.member.dto.MemberDTO;
import com.beonse2.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.beonse2.config.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.beonse2.config.exception.ErrorCode.NOT_MATCH_APPROVAL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MemberMapper memberMapper;
    private final AdminMapper adminMapper;

    public SuccessMessageDTO updateAcceptAdmin(Long memberId) {

        MemberDTO findMember = memberMapper.findById(memberId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        if (findMember.getIsApproval().equals("가입 대기")) {
            throw new CustomException(NOT_MATCH_APPROVAL);
        }

        adminMapper.updateAcceptApproval(memberId);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("가입 승인 완료")
                .build();
    }

    public SuccessMessageDTO updateRejectAdmin(Long memberId) {

        MemberDTO findMember = memberMapper.findById(memberId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMBER)
        );

        if (findMember.getIsApproval().equals("가입 대기")) {
            throw new CustomException(NOT_MATCH_APPROVAL);
        }

        adminMapper.updateRejectApproval(memberId);

        return SuccessMessageDTO.builder()
                .statusCode(HttpStatus.OK.value())
                .successMessage("가입 거절 완료")
                .build();
    }
}
