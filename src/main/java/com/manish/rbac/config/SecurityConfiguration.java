package com.manish.rbac.config;

import com.manish.rbac.authfilter.JwtFilter;
import com.manish.rbac.constant.ROLE;
import com.manish.rbac.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * SecurityConfiguration class handles the security setup for the application.
 */
@Configuration
@EnableWebSecurity // Enables Spring Security for the application
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter; // JWT filter used to authenticate requests
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint; // Custom entry point for unauthorized requests
    private static final String[] whiteListUrl = {  // Array of whiteList Url
            "/v1/auth/**",
            "/swagger-ui/index.html",
            "/v3/**",
            "/swagger-ui/**"
    };

    /**
     * Security filter chain configuration to define authorization and authentication mechanisms.
     * @param http HttpSecurity configuration for Spring Security
     * @return SecurityFilterChain that defines the security setup
     * @throws Exception If any error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        return http.authorizeHttpRequests(request -> request
                        // Allow public access to authentication-related endpoints
                        .requestMatchers(whiteListUrl).permitAll()
                        // Resource endpoints for 'USER', 'MODERATOR', and 'ADMIN' roles
                        .requestMatchers("/v1/resources/user").hasAnyRole(ROLE.USER, ROLE.MODERATOR, ROLE.ADMIN)
                        // Resource endpoints for 'MODERATOR' and 'ADMIN' roles
                        .requestMatchers("/v1/resources/moderator").hasAnyRole(ROLE.MODERATOR, ROLE.ADMIN)
                        // Resource endpoints for 'ADMIN' role only
                        .requestMatchers("/v1/resources/admin").hasRole(ROLE.ADMIN)
                        // Any other request requires authentication
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable()) // Disables CSRF as the app is stateless and relies on JWT for security
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)) // Use the custom entry point for unauthorized requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Session management is stateless (JWT-based)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Adds the JWT filter before the UsernamePasswordAuthenticationFilter
                .build();
    }
}
