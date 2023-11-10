package com.beonse2.config.utils.success;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class SuccessMessageDTO {

    private final SuccessCode successCode;

    @Builder
    public SuccessMessageDTO(SuccessCode successCode) {
        this.successCode = successCode;
    }
}
