package com.beonse2.config.utils.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SUCCESS_DELETE_BOARD(HttpStatus.OK.value(), "게시글이 정상적으로 삭제되었습니다");

    private final int statusCode;
    private final String successMessage;

}
