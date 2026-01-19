package com.sportisfun.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

//    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Key key;

    private final long tokenValidityMs;

    public JwtUtil(@Value("${security.jwt.validity-ms}")  long tokenValidityMs,
                   @Value("${security.jwt.secret}")String secret) {
        this.tokenValidityMs = tokenValidityMs;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + tokenValidityMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            log.warn("Token is expired", e);
        }catch (JwtException e){
            log.warn("Token is invalid", e);
        }
        return false;
    }

    public String extractUsername(String token) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
        }catch (ExpiredJwtException e){
            log.warn("Token is expired", e);
        }catch (JwtException e) {
            log.warn("Token is invalid", e);
        }
        return null;
    }
}
