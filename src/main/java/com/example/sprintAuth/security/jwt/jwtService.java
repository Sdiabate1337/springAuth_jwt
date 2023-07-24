package com.example.sprintAuth.security.jwt;

import java.security.Key;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;

import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
        return extractClaims(token,  Claims::getSubject);
    }

    public  <T> T extractClaims(
        String token, 
        Function<Claims, T> claimRsolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimRsolver.apply(claims);
    }

    public String generateToken(UserDetails UserDetails){
        return generateToken(new HashMap<>(), UserDetails);
    }

    public String generateToken(
       Map<String, Object> extraClaims,
        UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public  boolean  isTokenValid(String token, UserDetails UserDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(UserDetails.getUsername())) && !isTokenExpire(token);
    }

    private boolean isTokenExpire(String token) {
        return extractExpiration(token).before(new Date(0));
    }

    private java.util.Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
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





