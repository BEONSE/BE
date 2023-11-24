package com.beonse2.domain.member.dto;

import com.beonse2.domain.member.vo.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private Long branchId;
    private int statusCode;
    private String successMessage;
    private String nickname;
    private Role role;

    @Builder
    public LoginResponseDTO(Long branchId, int statusCode, String successMessage, String nickname, Role role) {
        this.branchId = branchId;
        this.statusCode = statusCode;
        this.successMessage = successMessage;
        this.nickname = nickname;
        this.role = role;
    }
}
