package com.manish.rbac.authfilter;

import com.manish.rbac.utilis.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtFilter is a custom filter that processes every HTTP request to check for a valid JWT token.
 * If a valid token is found, it sets the authentication context for the user.
 */
@Component
@RequiredArgsConstructor // Lombok annotation to generate a constructor for required fields
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService; // Service to load user details
    private final JwtUtil jwtUtil; // Utility class for JWT operations

    /**
     * Processes each request, checks for a JWT in cookies or headers, validates it,
     * and sets up the Spring Security context for authenticated users.
     *
     * @param request  The HTTP request
     * @param response The HTTP response
     * @param chain    The filter chain to pass the request/response to the next filter
     * @throws ServletException If an error occurs during filtering
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extract JWT from cookies
        Cookie[] cookies = request.getCookies();
        String authorizationHeader = ""; // Alternate approach: request.getHeader("Authorization");

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    authorizationHeader = cookie.getValue(); // Extract the JWT from the "Authorization" cookie
                }
            }
        }

        String jwt = null; // Holds the JWT token
        String id = null;  // Holds the user ID extracted from the token

        try {
            // Check if the Authorization header or cookie contains a Bearer token
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer+")) {
                jwt = authorizationHeader.substring(7); // Remove "Bearer+ " prefix to extract the JWT
                id = jwtUtil.extractId(jwt); // Extract the user ID from the token
            }

            // If a valid user ID is found and the user is not yet authenticated
            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details using the extracted user ID
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);

                // Validate the JWT token
                if (jwtUtil.validateToken(jwt)) {
                    // Create an authentication token for the user
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Attach request details to the authentication token
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication context
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (JwtException e) {
            // Handle JWT-specific exceptions (e.g., expired, malformed tokens)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set response status to 401 (Unauthorized)
            response.getWriter().write("JWT Error: " + e.getMessage()); // Return error message in the response
            return;

        } catch (Exception e) {
            // Handle general exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set response status to 500 (Internal Server Error)
            response.getWriter().write("An error occurred while processing the JWT token: " + e.getMessage());
            return;
        }

        // Continue processing the request through the filter chain
        chain.doFilter(request, response);
    }
}
