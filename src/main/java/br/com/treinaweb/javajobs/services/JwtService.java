package br.com.treinaweb.javajobs.services;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private static final String SIGNIN_KEY = "O4iQadaPyljNq81GB8H4hzMipRrE4ACk";

    private static final String REFRESH_SIGNIN_KEY = "gmIIK7pt42gbOojegZfyAQo5gPDvhDrW";

    private static final int EXPIRATION_TIME = 30;

    private static final int REFRESH_EXPIRATION_TIME = 60;

    public String generateToken(Authentication authentication) {
        return generateToken(SIGNIN_KEY, authentication.getName(), EXPIRATION_TIME);
    }
    
    public String generateRefreshToken(String username) {
        return generateToken(REFRESH_SIGNIN_KEY, username, REFRESH_EXPIRATION_TIME);
    }

    public Date getExpirationFromToken(String token) {
        Claims claims = getClaims(token, SIGNIN_KEY);

        return claims.getExpiration();
    }

    public String getUserNameFromToken(String token) {
        Claims claims = getClaims(token, SIGNIN_KEY);
        
        return claims.getSubject();
    }

    public String getUserNameFromRefreshToken(String refreshToken) {
        Claims claims = getClaims(refreshToken, REFRESH_SIGNIN_KEY);
        
        return claims.getSubject();
    } 

    private String generateToken(String signinKey, String subject, int expirationTime) {
        Map<String, Object> claims = new HashMap<>();

        Instant currentDate = Instant.now();
        Instant expirationDate = currentDate.plusSeconds(expirationTime);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(currentDate.toEpochMilli()))
            .setExpiration(new Date(expirationDate.toEpochMilli()))
            .signWith(SignatureAlgorithm.HS512, signinKey)
            .compact();
    }

    private Claims getClaims(String token, String signinKey) {
        return Jwts.parser()
            .setSigningKey(signinKey)
            .parseClaimsJws(token)
            .getBody();
    }
}
