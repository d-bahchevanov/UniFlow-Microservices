package com.example.gatewayserver.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private String secretKey;
    private String issuer;

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSecretKey() {
        return secretKey;
    }

}