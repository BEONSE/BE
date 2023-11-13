//package com.beonse2.config.filter;
//
//import com.beonse2.config.jwt.TokenProvider;
//import com.beonse2.domain.member.service.MemberDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@RequiredArgsConstructor
//public class CorsFilter implements Filter{
//
//    private final MemberDetailsService memberDetailsService;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        //AccessToken 얻기
//        String token = null; //초기화
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        //Authorization: Bearer token 으로 넘어옴.
//        String authorizationHeader =  httpServletRequest.getHeader("Authorization");
//        if(authorizationHeader != null && authorizationHeader.contains("Bearer")) {
//            token = authorizationHeader.substring(7); //"Bearer 이후의 값이 token 값이어서 가져올 것
//        }
//
//        //유효한 토큰인지 확인
//        if(token != null && !token.trim().equals("")) {
//            if(TokenProvider.validateToken(token)) { //토큰에서 userId 가져오기
//                String userId = TokenProvider.getEmail(token);
//
//                //DB에서 userId에 해당하는 정보를 가져오기
//                UserDetails userDetails = memberDetailsService.loadUserByUsername(userId);
//
//                //인증 객체 생성 -> 생성이 되면 스프링 시큐리티가 인증한 것
//                Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "",  userDetails.getAuthorities()); //이미 토큰이 통과 되면 password는 필요없기때문에 null
//
//                //Spring Security에 인증 객체 등록 -> 토큰이 유효하면 시큐리티에서 인증 작업 끝남
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            }
//        } //토큰이 유효하지 않으면 다음 필터로 넘어가서 인증처리 해야 함
//        chain.doFilter(request, response);
//    }
//
//       /* HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods","*");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization, accessToken, refreshToken, Cookie, Set-Cookie");
//        // response.setHeader("Access-Control-Expose-Headers", "accessToken, Set-Cookie");
//
//        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        }else {
//            chain.doFilter(req, res);
//        }*/
//}
