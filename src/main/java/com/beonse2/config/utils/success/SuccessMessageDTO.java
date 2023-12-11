package com.beonse2.config.utils.success;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessMessageDTO {

    private final int statusCode;
    private final String successMessage;

    @Builder
    SuccessMessageDTO(int statusCode, String successMessage) {
        this.statusCode = statusCode;
        this.successMessage = successMessage;
    }
}
