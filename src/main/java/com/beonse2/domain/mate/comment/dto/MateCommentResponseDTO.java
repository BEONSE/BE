package com.beonse2.domain.mate.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MateCommentResponseDTO {

    private Long mcid;
    private Long memberMid;
    private Long mateBoardMbid;
    private String nickname;
    private int paymentAmount;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @Builder
    public MateCommentResponseDTO(Long mcid, Long memberMid, Long mateBoardMbid, String nickname, int paymentAmount, String content, Timestamp createdAt) {
        this.mcid = mcid;
        this.memberMid = memberMid;
        this.mateBoardMbid = mateBoardMbid;
        this.nickname = nickname;
        this.paymentAmount = paymentAmount;
        this.content = content;
        this.createdAt = createdAt;
    }
}
