package com.uniflow.enrollservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;
    private Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(getKey())
                .requireIssuer(jwtProperties.getIssuer())
                .build().parseSignedClaims(token)
                .getPayload();
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
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }
}
