package com.meta.ale.jwt;


import com.meta.ale.domain.UserDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${ale.jwt.jwtSecret}")
    private String jwtSecret;
    @Value("${ale.jwt.jwtExpirationMs}")
    private Long jwtExpirationMs;
    @Value("${ale.jwt.jwtCookieName}")
    private String jwtCookie;
    @Value("${ale.jwt.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    public ResponseCookie generateJwtCookie(UserDto userDto) {
        String jwt = generateTokenFromUsername(userDto.getEmpNum());    // JWT 토큰을 생성
//        return generateCookie(jwtCookie, jwt, "/api");
        return generateCookie(jwtCookie, jwt, "/");
    }

    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
//        return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken");
        return generateCookie(jwtRefreshCookie, refreshToken, "/");
    }


    public ResponseCookie getCleanJwtCookie() {
//        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
        return cookie;
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
//        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refreshtoken").build();
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        // setSigningKey() 메소드를 이용하여 서명에 사용된 비밀키(jwtSecret)를 설정
        // parseClaimsJws() 메소드를 이용하여 JWT 토큰을 파싱하여 JWS 객체를 반환
        // 그리고 getBody() 메소드는 JWS 객체에서 페이로드를 추출
        // JWT 토큰의 페이로드는 Claims라는 키-값 쌍으로 이루어져 있으며, 이를 추출하기 위해 getBody() 메소드를 사용
        // 추출된 Claims에서 getSubject() 메소드를 이용하여 유저명(UserName)을 추출하고 반환
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("토큰 유효 검사");
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("WT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value)    // // name 이름을 가진 쿠키를 생성
                .path(path).maxAge(60 * 60 * 24)
                .httpOnly(true).build();
        // path 메서드로 쿠키의 경로를 '/api'로 지정하고,
        // maxAge() 메소드를 사용하여 쿠키의 만료 시간을 24시간으로 설정
        // httpOnly() 메소드를 사용하여 쿠키에 접근할 수 있는 수단을 HTTP 요청과 응답 이외에는 제한
        return cookie;
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}
