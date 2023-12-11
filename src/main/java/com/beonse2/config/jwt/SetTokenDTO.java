package com.beonse2.config.jwt;

import com.beonse2.domain.member.vo.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SetTokenDTO {

    private Long mid;
    private String email;
    private String name;
    private String nickname;
    private String password;
    private Role role;

    @Builder
    public SetTokenDTO(Long mid, String email, String name, String nickname, String password, Role role) {
        this.mid = mid;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
