package com.beonse2.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GenericFillterBean을 Extends에서 doFilter Override,
 * 실제 실터링 로직은 doFilter 내부에 작성
 */

@Slf4j
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

        // public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtUtil tokenProvider;

    @Autowired
    private MemberDetailsService memberDetailsService;

    public JwtAuthenticationFilter(JwtUtil tokenProvider) {
        this.tokenProvider = tokenProvider;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //AccessToken 얻기
        String accessToken = null; //초기화
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;


        //Authorization: Bearer token 으로 넘어옴.
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.contains("Bearer")) {
            //헤더에 accessToken이 포함되어 있을 경우
            accessToken = tokenProvider.resolveToken(authorizationHeader);
        } else {
            //QueryString 형태로 넘어왔을 경우
            //<img src="battach/3?accessToken=xxxxx">
            accessToken = request.getParameter("accessToken");
        }

        try {
            //유효한 토큰인지 확인
            if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
                if (JwtUtil.validateToken(accessToken)) { //토큰에서 email 가져오기

                    //인증 객체 생성 -> 생성이 되면 스프링 시큐리티가 인증한 것
                    Authentication authentication = tokenProvider.getAuthentication(accessToken);

                    //Spring Security에 인증 객체 등록 -> 토큰이 유효하면 시큐리티에서 인증 작업 끝남
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Security Contextx에 '{}' 인증 정보를 저장했습니다", authentication.getName());
                }
            } else {//토큰이 유효하지 않으면 다음 필터로 넘어가서 인증처리 해야 함
                log.info("유효한 JWT 토큰이 없습니다");
            }
            chain.doFilter(request, response);
            // 잘못된 서명인 토큰일 경우
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED, httpServletResponse, e);

            // 만료된 토큰일 경우
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED, httpServletResponse, e);
        }
        // 지원되지 않는 토큰일 경우
        catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED, httpServletResponse, e);

            // 잘못된 토큰일 경우
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            setErrorResponse(HttpStatus.UNAUTHORIZED, httpServletResponse, e);
        }
    }


    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
        JSONObject responseJson = new JSONObject();
        try {
            responseJson.put("HttpStatus", HttpStatus.UNAUTHORIZED);
            responseJson.put("message", ex.getMessage());
            responseJson.put("status", false);
            responseJson.put("statusCode", 401);
            responseJson.put("code", "Tk401");
            response.getWriter().print(responseJson);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
