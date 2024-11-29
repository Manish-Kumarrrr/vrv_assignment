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

@Component // Marks this class as a Spring component to be managed by the Spring container
public class JwtUtil {

    // Load the secret key for JWT signing from application properties or YAML
    @Value("${secret.key}")
    private String SECRET_KEY;

    /**
     * Returns the signing key used for JWT token signing and verification.
     * @return SecretKey for JWT signing
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Extracts the user ID (subject) from the JWT token.
     * @param token The JWT token
     * @return The extracted user ID
     */
    public String extractId(String token) {

        // Extracts all claims from the token and retrieves the subject (user ID)
        Claims claims = extractAllClaims(token);
        return claims.getSubject(); // Returns the subject (user ID)
    }

    /**
     * Extracts the expiration date from the JWT token.
     * @param token The JWT token
     * @return The expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration(); // Retrieves the expiration from claims
    }

    /**
     * Extracts all claims from the JWT token.
     * @param token The JWT token
     * @return Claims containing all the details encoded in the token
     */
    private Claims extractAllClaims(String token) {
        // Parses the token and verifies it with the signing key to get the claims
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Generates a JWT token for a given user.
     * @param user The user for whom the token is generated
     * @return The generated JWT token
     */
    public String generateToken(User user) {
        // Prepare the claims for the token (e.g., roles of the user)
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        return createToken(claims, user.getId()); // Create the token with the claims and user ID
    }

    /**
     * Creates the JWT token with custom claims and subject (user ID).
     * @param claims Custom claims to be included in the token
     * @param id The subject (user ID) of the token
     * @return The created JWT token
     */
    private String createToken(Map<String, Object> claims, String id) {
        // Build the JWT token with claims, subject (user ID), issued time, expiration, and signing
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(id) // Set user ID as the subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50)) // Set expiration time (50 minutes)
                .setHeaderParam("typ", "JWT") // Set the JWT header type
                .signWith(getSigningKey()) // Sign the token with the signing key
                .compact(); // Build and return the token
    }

    /**
     * Validates the JWT token by checking its expiration.
     * @param token The JWT token
     * @return True if the token is valid (not expired), false otherwise
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token); // Validate by checking expiration
    }

    /**
     * Checks if the JWT token has expired.
     * @param token The JWT token
     * @return True if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare expiration date with current time
    }
}
