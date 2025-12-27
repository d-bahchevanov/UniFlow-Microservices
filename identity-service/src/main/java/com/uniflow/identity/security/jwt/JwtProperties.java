package com.uniflow.identity.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.jwt.token")
public class JwtProperties {
    private String secretKey;
    private long expireLength;
    private final String issuer = "uniflow-identity-service";
}