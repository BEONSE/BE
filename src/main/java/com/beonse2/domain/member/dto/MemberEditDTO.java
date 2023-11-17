package com.beonse2.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class MemberEditDTO {

    private Long mid;

    private String email;

    private String nickname;

    private String password;

    private String address;

    private File image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp modifiedAt;

    @Builder

    public MemberEditDTO(Long mid, String email, String nickname, String password, String address, File image, Timestamp modifiedAt) {
        this.mid = mid;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.image = image;
        this.modifiedAt = modifiedAt;
    }
}
