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
    private byte[] imageData;
    private int grade;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @Builder
    public MateCommentResponseDTO(Long mcid, Long memberMid, Long mateBoardMbid, String nickname, int paymentAmount, byte[] imageData, String content, Timestamp createdAt) {
        this.mcid = mcid;
        this.memberMid = memberMid;
        this.mateBoardMbid = mateBoardMbid;
        this.nickname = nickname;
        this.paymentAmount = paymentAmount;
        this.imageData = imageData;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void updateGrade(int grade) {
        this.grade = grade;
    }
}
