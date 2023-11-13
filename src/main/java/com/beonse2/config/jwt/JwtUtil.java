//package com.beonse2.config.jwt;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//s
//@Slf4j
//public class JwtUtil {
//
//    //비밀키(누출되면 안됨). 개인키. 임의로 작성해 주면 됨
//    private static final String secretKey = "secret";
//
//    // JWT 토큰 생성: 사용자 아이디 포함
//    public static String createToken(String userId) {
//        String token = null;
//        try {
//            JwtBuilder builder = Jwts.builder();
//            //header 생성 부분
//            builder.setHeaderParam("typ", "JWT"); //토큰의 종류
//            builder.setHeaderParam("alg", "HS256"); //암호화 알고리즘 종류
//
//            //payload 생성 부분 . 이 부분만 수정해서 사용하면 됨
//            builder.setExpiration(new Date(new Date().getTime() + 1000*60*60*12)); //토큰의 유효기간 : 12시간
//            builder.claim("userId", userId); //토큰에 저장되는 데이터
//
//            //signature의 secret 키 . 비밀키 or 서버키
//            builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")); //비밀키
//            token = builder.compact(); //모든 내용을 묶기
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return token;
//    }
//
//    //JWT 토큰에서 모든 내용(Claims) 얻기
//    public static Claims getClaims(String token) {
//        Claims claims = null;
//        try {
//            //토큰 해석
//            JwtParser parser = Jwts.parser();
//            parser.setSigningKey(secretKey.getBytes("UTF-8"));
//            //위에 secretKey 넣고여기서 복호화 해줌
//            Jws<Claims> jws = parser.parseClaimsJws(token);
//            claims = jws.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return claims;
//    }
//
//    //JWT 토큰에서 사용자 아이디 얻기
//    public static String getEmail(String token) {
//        String email = null;
//        try {
//
//            //토큰 해석
//            JwtParser parser = Jwts.parser();
//            parser.setSigningKey(secretKey.getBytes("UTF-8"));
//            //위에 secretKey 넣고여기서 복호화 해줌
//            Jws<Claims> jws = parser.parseClaimsJws(token);
//            Claims claims = jws.getBody(); //Claims -> payLoad
//            //복호화 해서 id를 받아올 수 있음
//            email = claims.get("userId", String.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return email;
//    }
//
//    //JWT 토큰 유효성 검사: 만료일자 확인
//    public static boolean validateToken(String token) {
//        boolean validate = false;
//        try {
//            JwtParser parser = Jwts.parser();
//            //복호화하기 위해서 secretKey 넣어줌
//            parser.setSigningKey(secretKey.getBytes("UTF-8"));
//            Jws<Claims> jws = parser.parseClaimsJws(token);
//            Claims claims = jws.getBody();
//            //만료기간 확인 -> true : 유효, false : 만료
//            validate = !claims.getExpiration().before(new Date());
//         /*if(validate) {
//            long remainMillSecs = claims.getExpiration().getTime() - new Date().getTime();
//            logger.info("" + remainMillSecs/1000 + "초 남았음");
//         }*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return validate;
//    }
//
//    //테스트
////   public static void main(String[] args) {
////      //토큰 생성
////      String jwt = createToken("user1");
////      log.info(jwt);
////
////      //토큰 정보 얻기 : 유효하면 값을 받아옴
////      if(validateToken(jwt)) {
////         String uid = getUserId(jwt);
////         log.info(uid);
////      }
////   }
//}