package com.beonse2.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND.value(), "게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND.value(), "해당 게시글에 댓글이 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND.value(), "해당 댓글에 대댓글이 없습니다.");

    private final int statusCode;
    private final String errorMessage;
}
