package com.manish.rbac.utilis;

import com.manish.rbac.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Load secret key from application properties or YAML
    @Value("${secret.key}")
    private String SECRET_KEY;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Extract username (subject) from the JWT token
    public String extractId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Extract expiration time from the JWT token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Set the correct signing key
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Extract the payload (claims)
    }


    // Generate a JWT token for a given id
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",user.getRoles());
        return createToken(claims, user.getId());
    }

    // Create the JWT token with custom claims and subject
    private String createToken(Map<String, Object> claims, String id) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50)) // 50 minutes expiration time
                .setHeaderParam("typ", "JWT") // Set custom header parameter
                .signWith(getSigningKey())
                .compact();
    }

    // Validate the JWT token (check expiration)
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // Check if the JWT token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
