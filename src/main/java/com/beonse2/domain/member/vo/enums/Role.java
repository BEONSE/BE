package com.beonse2.domain.member.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Role {
    ROLE_USER("일반회원"),
    ROLE_BRANCH("지점사"),
    ROLE_ADMIN("관리자");

    String role;

    public String value() {
        return this.role;
    }
}
