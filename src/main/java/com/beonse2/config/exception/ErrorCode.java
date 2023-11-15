package com.beonse2.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND.value(), "게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND.value(), "해당 게시글에 댓글이 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND.value(), "해당 댓글에 대댓글이 없습니다."),
    NOT_MATCH_PAYMENT_PRICE(HttpStatus.BAD_REQUEST.value(), "잘못된 결제 금액 입니다."),
    WRONG_CARD_NAME(HttpStatus.BAD_REQUEST.value(), "카드사 이름이 잘못되었습니다."),
    WRONG_CARD_NUMBER(HttpStatus.BAD_REQUEST.value(), "잘못된 카드번호 입니다."),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND.value(), "결제 내역이 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "만료된 토큰 입니다."),
    NOT_FOUND_COUPON(HttpStatus.NOT_FOUND.value(), "보유중인 쿠폰이 없습니다."),
    UPDATE_NOT_NULL(HttpStatus.BAD_REQUEST.value(), "NULL로 수정할 수 없습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(), "회원을 찾을 수 없습니다."),
    DUPLICATE_MEMBER(HttpStatus.BAD_REQUEST.value(), "이미 가입된 회원입니다"),
    NOT_MATCH_EMAIL(HttpStatus.BAD_REQUEST.value(), "입력한 이메일의 회원이 없습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.NOT_FOUND.value(), "잘못된 비밀번호입니다."),
    FAILED_UPDATE(HttpStatus.BAD_REQUEST.value(), "수정에 실패했습니다."),
    NOT_MATCH_USER(HttpStatus.BAD_REQUEST.value(), "작성자가 아닙니다.");


    private final int statusCode;
    private final String errorMessage;
}
