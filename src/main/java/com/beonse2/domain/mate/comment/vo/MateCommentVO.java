package com.beonse2.domain.mate.comment.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MateCommentVO {

    private Long mcid;
    private Long memberMid;
    private Long mateBoardMbid;
    private String content;

    @Builder
    public MateCommentVO(Long mcid, Long memberMid, Long mateBoardMbid, String content) {
        this.mcid = mcid;
        this.memberMid = memberMid;
        this.mateBoardMbid = mateBoardMbid;
        this.content = content;
    }
}
