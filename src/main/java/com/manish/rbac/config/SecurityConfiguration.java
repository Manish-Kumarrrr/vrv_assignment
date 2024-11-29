package com.manish.rbac.config;

import com.manish.rbac.authfilter.JwtFilter;
import com.manish.rbac.constant.ROLE;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfiguration class handles the security setup for the application.
 * It configures HTTP security, JWT filter integration, and authorization rules.
 */
@Configuration
@EnableWebSecurity // Enables Spring Security for the application
@RequiredArgsConstructor // Automatically generates constructor for the class, injecting dependencies
public class SecurityConfiguration {

    private final JwtFilter jwtFilter; // JWT filter used to authenticate requests

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
                        .requestMatchers("/v1/auth/**").permitAll()
                        // Resource endpoints for 'USER', 'MODERATOR', and 'ADMIN' roles
                        .requestMatchers("/v1/resources/user").hasAnyRole(ROLE.USER,ROLE.MODERATOR,ROLE.ADMIN)
                        // Resource endpoints for 'MODERATOR' and 'ADMIN' roles
                        .requestMatchers("/v1/resources/moderator").hasAnyRole(ROLE.MODERATOR,ROLE.ADMIN)
                        // Resource endpoints for 'ADMIN' role only
                        .requestMatchers("/v1/resources/admin").hasRole(ROLE.ADMIN)
                        // Any other request requires authentication
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF as the app is stateless and relies on JWT for security
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Session management is stateless (JWT-based)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Adds the JWT filter before the UsernamePasswordAuthenticationFilter
                .build();
    }
}
