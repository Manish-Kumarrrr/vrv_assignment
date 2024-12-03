package com.manish.rbac.authfilter;

import com.manish.rbac.utilis.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

@Component
@RequiredArgsConstructor // Lombok annotation to generate constructor with required arguments
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService; // Service to load user details
    private final JwtUtil jwtUtil; // Utility class for JWT operations

    /**
     * This method is executed for every HTTP request. It checks for a valid JWT in the Authorization header,
     * validates it, and if valid, sets the authentication context for the user.
     *
     * @param request  The HTTP request
     * @param response The HTTP response
     * @param chain The filter chain to continue the processing of the request
     * @throws ServletException If an error occurs during filtering
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;
        String id = null;

        try {
            // Check if the Authorization header contains the Bearer token
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7); // Extract the token part
                id = jwtUtil.extractId(jwt); // Extract the user id from the JWT
            }

            // If an id is found (user is authenticated)
            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load the user details using the id (username)
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);

                // If the JWT is valid, authenticate the user
                if (jwtUtil.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        }catch (JwtException e) {
            // Handle other JWT-related exceptions
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT Error: " + e.getMessage());
            return;

        } catch (Exception e) {
            // Handle general exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while processing the JWT token: " + e.getMessage());
            return;
        }

        // Continue the request processing through the filter chain
        chain.doFilter(request, response);
    }
}
