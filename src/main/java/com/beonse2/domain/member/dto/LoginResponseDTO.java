package com.beonse2.domain.member.dto;

import com.beonse2.domain.member.vo.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private Long branchId;
    private int statusCode;
    private String successMessage;
    private Role role;

    @Builder
    public LoginResponseDTO(Long branchId, int statusCode, String successMessage, Role role) {
        this.branchId = branchId;
        this.statusCode = statusCode;
        this.successMessage = successMessage;
        this.role = role;
    }
}
