package com.beonse2.domain.reviewBoard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.security.Timestamp;

@Getter
@NoArgsConstructor
public class ReviewBoard {

    private Long rbId; //고유 번호

    private Long memberMid;//회원 번호

    private Long branchBid;//가맹점 번호

    private Long couponCid;//쿠폰 번호

    private String title;//제목

    private String content;//내용

    private boolean status;//게시글 삭제

    private File image;

    private Timestamp createdAt;//작성일

    private  Timestamp modifiedAt;//수정일

}
