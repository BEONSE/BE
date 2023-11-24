package com.beonse2.domain.admin.dto;

import com.beonse2.domain.member.vo.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class BranchMemberDTO {

    private int rnum;
    private Long mid;
    private Long bid;
    private String email;
    private String name;
    private String nickname;
    private Role role;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:dd", timezone = "Asia/Seoul")
    private Timestamp createdAt;

}
