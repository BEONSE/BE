package com.beonse2.domain.member.dto;

import com.beonse2.domain.member.vo.enums.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {//회원 가입, 수정 요청 클래스

    private Long id;//고유번호
    private String email;//email
    private String nickname;//닉네임
    private String password;//PW
    private String name;//이름
    private String address;//주소
    private Role role;//권한

    //비밀번호 암호화 기능
    public void encodingPassword(PasswordEncoder passwordEncoder) {
        if (StringUtils.isEmpty(password)) {
            return;
        }
        password = passwordEncoder.encode(password);
    }
}
