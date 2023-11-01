package com.java.udemy.security;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${server.jwtSecret}")
    private String jwtSecret;

    @Value("${server.jwtExpirationMs}")
    private long jwtExpirationMs = 3600000;

    private Key key() {
        return new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), 
        SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String token = Jwts.builder()
                .claim("name", userDetails.getFullname())
                .claim("email", userDetails.getEmail())
                .setSubject((userDetails.getEmail()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key())
                .compact();
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
