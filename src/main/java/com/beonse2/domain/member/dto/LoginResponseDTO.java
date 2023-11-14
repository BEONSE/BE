package com.beonse2.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
    private int statusCode;
    private String successMessage;

    @Builder
    public LoginResponseDTO(String accessToken, String refreshToken, int statusCode, String successMessage) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.statusCode = statusCode;
        this.successMessage = successMessage;
    }
}
