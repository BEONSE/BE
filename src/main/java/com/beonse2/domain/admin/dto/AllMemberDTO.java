package com.beonse2.domain.admin.dto;

import com.beonse2.domain.member.vo.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class AllMemberDTO {

    private int rnum;
    private Long mid;
    private String email;
    private String nickname;
    private String name;
    private String address;
    private Role role;
    private int paymentAmount;
    private boolean status;
    private int pointAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:dd", timezone = "Asia/Seoul")
    private Timestamp createdAt;

}
