package com.beonse2.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class MemberEditDTO {

    private Long mid;
    private String email;
    private String nickname;
    private String password;
    private String address;
    private String originalFileName;
    private String imageType;
    private byte[] imageData;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;

    @Builder
    public MemberEditDTO(Long mid, String email, String nickname, String password, String address,
                         String originalFileName, String imageType, byte[] imageData, Timestamp modifiedAt) {
        this.mid = mid;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.originalFileName = originalFileName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.modifiedAt = modifiedAt;
    }
}
