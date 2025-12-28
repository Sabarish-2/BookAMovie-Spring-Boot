package com.moviebookingapp.user_module.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET_KEY = "Very Secret key - To Be Changed Later!..&^%&^*&*";

    public String generateToken(String loginID, String role) {
        return Jwts.builder()
                .subject(loginID)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (3600_000)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    public String extractLoginID(String token) {
        return getClaims(token).getSubject();
    }
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }
    public boolean validateToken(String token) {
        getClaims(token);
        return true;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
