package com.beonse2.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
    private int statusCode;
    private String successMessage;
    private String nickname;

    @Builder
    public LoginResponseDTO(String accessToken, String refreshToken, int statusCode, String successMessage, String nickname) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.statusCode = statusCode;
        this.successMessage = successMessage;
        this.nickname = nickname;
    }
}
