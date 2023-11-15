package com.beonse2.config.jwt;

import com.beonse2.domain.member.dto.MemberDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil implements InitializingBean {

    // private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final String secret;
    private static Key key;

    @Autowired
    private MemberDetailsService memberDetailsService;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 액세스 토큰 생성 메서드
     *
  //   * @param email 발급받는 유저의 아이디
 //    * @param role  발급받는 유저의 권한
     * @return 발급받은 토큰을 리턴해줌
     */
    public String createAccessToken(MemberDTO memberDTO) {
        Claims claims = Jwts.claims().setSubject(memberDTO.getEmail());
        claims.put("memberDTO", memberDTO);
        // 토큰 만료기간
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.accessTokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(memberDTO.getEmail())
                .setClaims(claims)
                .setIssuedAt(now) //토큰 발행 시간정보
                .setExpiration(validity) // 토큰 만료일 설정
                .signWith(key, SignatureAlgorithm.HS512) // 암호화
                .compact();
    }
    /**
     * 리프레시 토큰 생성 메서드
     *
  //   * @param email 발급받는 유저의 아이디
 //    * @param role  발급받는 유저의 권한
     * @return 발급받은 토큰을 리턴해줌
     */
    public String createRefreshToken(MemberDTO memberDTO) {
        Claims claims = Jwts.claims().setSubject(memberDTO.getEmail());
        claims.put("memberDTO", memberDTO);
        // 토큰 만료기간
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.accessTokenValidityInMilliseconds);
        return Jwts.builder()
                .setSubject(memberDTO.getEmail())
                .setClaims(claims)
                .setIssuedAt(now) //토큰 발행 시간정보
                .setExpiration(validity) // 토큰 만료일 설정
                .signWith(key, SignatureAlgorithm.HS512) // 암호화
                .compact();
    }

    /**
     * Token에 담겨있는 정보를 이용해 Authentication 객체를 반환하는 메서드
     */
    public Authentication getAuthentication(String token) {

        String userId = getEmail(token);
        UserDetails userDetails =
                memberDetailsService.loadUserByUsername(userId);
        System.out.println("getEmail(token) " +userId);
        System.out.println("UserDetails: " + userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // // 유저 이름 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Request header에서 token 꺼내옴
    public String resolveToken(String token) {//"Bearer " : "TOKEN값'
        // String token = request.getHeader("Authorization");
        // 가져온 Authorization Header 가 문자열이고, Bearer 로 시작해야 가져옴
        if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
            return token.substring(7);
        }
        return null;
    }

    /**
     * 토큰을 파싱하고 발생하는 예외를 처리, 문제가 있을경우 false 반환
     */
    public static boolean validateToken(String token) {

        /*Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        return !claims.getBody().getExpiration().before(new Date());*/
        boolean validate = false;
        try {
            JwtParser parser = Jwts.parser();
            //복호화하기 위해서 secretKey 넣어줌
            parser.setSigningKey(key);
            Jws<Claims> jws = parser.parseClaimsJws(token);
            Claims claims = jws.getBody();
            //만료기간 확인 -> true : 유효, false : 만료
            validate = !claims.getExpiration().before(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return validate;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
