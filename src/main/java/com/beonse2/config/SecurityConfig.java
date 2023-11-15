package com.beonse2.config;


import com.beonse2.config.jwt.JwtAccessDeniedHandler;
import com.beonse2.config.jwt.JwtAuthenticationEntryPoint;
import com.beonse2.config.jwt.JwtSecurityConfig;
import com.beonse2.config.jwt.TokenProvider;
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
    private final TokenProvider tokenProvider;
//    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    // private final JwtFilter jwtFilter;

    public SecurityConfig(
            TokenProvider tokenProvider,
//            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
            // JwtFilter jwtFilter


    ) {
        this.tokenProvider = tokenProvider;
//        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        // this.jwtFilter = jwtFilter;

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())    // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .and().csrf().disable()
                .httpBasic().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // jwt token으로 인증하므로 stateless 하도록 처리.

        http.authorizeRequests()
                .antMatchers("/api/v1/get").permitAll()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers("/api/v1/join").permitAll();
        http
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);
        // .expressionHanling
        // .antMatchers("/api/").permitAll();
        // .antMatchers("/").permitAll()
        // .anyRequest().authenticated();     // 나머지 모든 요청 불허  ( 생략 가능 )

        http.apply(new JwtSecurityConfig(tokenProvider));

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("https://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("AccessToken");
        configuration.addExposedHeader("RefreshToken");
        configuration.addExposedHeader("Origin");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

