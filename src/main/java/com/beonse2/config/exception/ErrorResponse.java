package com.beonse2.config.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponse {

    private final int statusCode;
    private final String errorMessage;

    @Builder
    public ErrorResponse(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ErrorResponse.builder()
                        .errorMessage(errorCode.getErrorMessage())
                        .statusCode(errorCode.getStatusCode())
                        .build());
    }

}
