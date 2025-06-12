package com.example.newsfeed_myself1.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // ✅ 비밀키: 실무에서는 환경변수로 뺌 (지금은 하드코딩으로 충분)
    private final String SECRET_KEY = "long-long-long-long-long-long-long";

    // ✅ 토큰 만료 시간 (1시간 = 1000ms * 60s * 60m)
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    /**
     * ✅ JWT 토큰 생성
     * @param userId - 토큰에 담을 사용자 ID
     * @return 생성된 JWT 문자열
     */
    public String createJwt(Long userId) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setSubject(userId.toString())                          // 사용자 ID를 subject에 저장
                .setIssuedAt(new Date())                               // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(key)                                      // 서명 (HMAC SHA256)
                .compact();                                         // 최종 토큰 문자열 반환
    }

    /**
     * ✅ JWT 토큰 검증 후 사용자 ID 추출
     * @param token - 클라이언트가 보낸 토큰 문자열
     * @return 사용자 ID (long 타입)
     */
    public Long verifyToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject()); // 사용자 ID만 추출해서 반환
    }
}
