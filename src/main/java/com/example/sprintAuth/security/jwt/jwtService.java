package com.example.sprintAuth.security.jwt;

import java.security.Key;
import java.util.Base64;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class jwtService {
    private static final String SECRET_KEY = "sW82WLr9ZFPS9G49MbjXxw364OMU43PJ";

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the extracted username
     */
    public String extractUsername(String token) {
        // Extract the username from the token
        // Implement your logic here to extract the username from the token
        return null;
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the extracted claims
     */
    public Claims extractAllClaims(String token) {
        // Extract all claims from the token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT signature verification.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}





