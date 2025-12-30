package com.uniflow.identity.security.jwt.service;
import com.uniflow.identity.model.User;
import com.uniflow.identity.security.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());
         return Jwts.builder().claims().add(claims)
                .subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpireLength()))
                .and().signWith(getKey()).issuer(jwtProperties.getIssuer())
                .compact();
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }
    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration().after(new Date());
    }
    private Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
