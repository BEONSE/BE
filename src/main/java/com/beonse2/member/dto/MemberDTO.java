package com.beonse2.member.dto;

import com.beonse2.member.vo.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO implements UserDetails {

    private Long mid;
    private String email; //email -> id
    private String name;
    private String nickname; //닉네임
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // 해당 필드는 오직 쓰려는 경우(deserialize)에만 접근이 허용
    //사용자를 생성하기 위한 요청 본문을 처리할 때는 사용되고, 응답결과를 생성할 때는 해당 필드는 제외되서 응답 본문에 표시x
    private String password; //비밀번호
    private String address;//주소
    private Role role; // 권한
    private Long paymentAmount;//누적 결제 내역
    private boolean status;//회원 삭제
    private File image;//프로필 사진
    private Timestamp createdAt;//생성일
    private Timestamp modifiedAt;//수정일

    //security 이용 코드
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //사용자가 가지는 권한 정보
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() { //ID 반환. 우리는 Email 반환
        return this.email;
    }

   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {// 사용자 계정 만료 여부 (true: 만료안됨)
        return true;
    }

   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {//사용자 잠겨있는지 (true: 잠기지 않음)
        return true;
    }

   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {//비밀번호 만료여부 (true: 만료안됨)
        return true;
    }

  //  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {// 계정 활성화(사용가능) 여부 (true: 활성화)
        return true;
    }
}
