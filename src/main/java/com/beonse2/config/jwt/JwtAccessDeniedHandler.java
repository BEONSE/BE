package com.beonse2.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {//SecurityConfig에 예외 처리를 위해 설정해놓은 클래스. 인증(Authentication)이 실패시 실행

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN); //403 에러
       /* response.setCharacterEncoding("utf-8");
        response.sendError(403, "권한이 없습니다.");*/
    }
}
