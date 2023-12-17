package com.java.udemy.config.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.java.udemy.config.Constants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${server.jwtSecret}")
    private String jwtSecret;

    @Value("${server.jwtExpirationMs}")
    private long jwtExpirationMs = 3600000;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
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
                .signWith(key(), SignatureAlgorithm.HS256)
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
            logger.error(Constants.MESSAGE_INVALID_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error(Constants.MESSAGE_TOKEN_EXPIRED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error(Constants.MESSAGE_TOKEN_UNSUPPORTED, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(Constants.MESSAGE_TOKEN_CLAIM_EMPTY, e.getMessage());
        }

        return false;
    }
}
