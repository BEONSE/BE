package com.beonse2.config;


import com.beonse2.config.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity//모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만듦
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtUtil tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtUtil tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //CORS 설정 . msa 로 구성시 필요. 단일 서버에서는 사용 안해도 됨. 도메인, 포트, 프로토콜이 다를 경우 사용
        http.cors().configurationSource(corsConfigurationSource())    // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                //사이트간 요청 위조 방지 비활성화. 시큐리티에 기본적으로 적용되어 있음
                .and().csrf().disable()

                //폼을 통한 인증 사용하지 않음
                .formLogin().disable()
                //Authorization 헤더를 통한 인증 사용하지 않음
                .httpBasic().disable();
        //서버 세션 비활성화. JWT 사용할 때만 비활성화
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // jwt token으로 인증하므로 stateless 하도록 처리.

        //JWT 토큰 인증 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/reservation/*").hasRole("USER")
                .antMatchers("/main").permitAll()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers("/api/v1/join").permitAll();

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http.apply(new JwtSecurityConfig(tokenProvider));

    }

    //크로스 도메인 설정 .   http.cors(config -> {}); 실헹하기 위한 객체
    @Bean// 스프링 관리 빈에 객체로 설정되기 위해 사용
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("https://localhost:3000");
        //모든 요청 헤더 허용
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("AccessToken");
        configuration.addExposedHeader("RefreshToken");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

