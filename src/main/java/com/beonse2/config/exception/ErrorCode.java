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
    NOT_USE_COUPON(HttpStatus.NOT_FOUND.value(), "이용된 쿠폰이 없습니다."),
    NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND.value(), "예약이 없습니다."),
    UPDATE_NOT_NULL(HttpStatus.BAD_REQUEST.value(), "NULL로 수정할 수 없습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(), "회원을 찾을 수 없습니다."),
    NOT_FOUND_BRANCH(HttpStatus.NOT_FOUND.value(), "가맹점 회원을 찾을 수 없습니다."),
    DUPLICATE_MEMBER(HttpStatus.BAD_REQUEST.value(), "이미 가입된 회원입니다"),
    DUPLICATE_BRANCH(HttpStatus.BAD_REQUEST.value(), "이미 가입된 가맹점입니다"),
    NOT_MATCH_EMAIL(HttpStatus.BAD_REQUEST.value(), "입력한 이메일의 회원이 없습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.NOT_FOUND.value(), "잘못된 비밀번호입니다."),
    FAILED_UPDATE(HttpStatus.BAD_REQUEST.value(), "수정에 실패했습니다."),
    NOT_MATCH_USER(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    NOT_MATCH_BRANCH(HttpStatus.FORBIDDEN.value(), "일치하는 가맹점이 없습니다."),
    NOT_FOUND_INFO(HttpStatus.NOT_FOUND.value(), "회원 정보를 찾을 수 없습니다."),
    DONT_WRITE_REVIEW(HttpStatus.FORBIDDEN.value(), "쿠폰 사용자만 리뷰를 작성할 수 있습니다."),
    ALREADY_WRITTEN_REVIEW(HttpStatus.FORBIDDEN.value(), "이미 리뷰를 작성한 쿠폰입니다."),
    NOT_FOUND_BRANCH_NAMES(HttpStatus.NOT_FOUND.value(), "지점명을 찾을 수 없습니다."),
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND.value(), "이미지가 없습니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 닉네임입니다."),
    NOT_MATCH_APPROVAL(HttpStatus.BAD_REQUEST.value(), "가입 대기 회원이 아닙니다."),
    WAITING_JOIN(HttpStatus.FORBIDDEN.value(), "가입 승인 대기중인 회원입니다."),
    REJECT_JOIN(HttpStatus.FORBIDDEN.value(), "가입이 거절된 회원입니다."),
    EMPTY_IMAGE(HttpStatus.BAD_REQUEST.value(), "이미지가 없습니다.");

    private final int statusCode;
    private final String errorMessage;
}
