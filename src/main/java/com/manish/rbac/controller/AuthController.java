package com.manish.rbac.controller;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;
import com.manish.rbac.service.AuthControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * AuthController handles all authentication-related endpoints such as user registration, login, and logout.
 * It interacts with the AuthControllerService to perform the necessary actions for user authentication.
 */
@RestController
@RequestMapping("/v1/auth") // Base URL for authentication-related endpoints
@RequiredArgsConstructor // Lombok's annotation to automatically generate a constructor for the final fields
@Tag(name = "Authentication API", description = "APIs for user authentication and authorization")
public class AuthController {

    private final AuthControllerService authControllerService; // Service layer for authentication logic

    @Value("${cookie.expireTime}") // Reads cookie expiration time from application properties
    private Integer expireTime;

    @Value("${env}") // Reads environment setting (e.g., 'prod' or 'dev') from application properties
    private String environment;

    /**
     * Registers a new user. The request body contains the user's registration details.
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user and stores the information in the database."
    )
    public ResponseEntity<RegisterResponse> registerUser(
            @Parameter(description = "Details of the user to register", required = true)
            @RequestBody RegisterRequest registerRequest,
            HttpServletResponse response) {

        RegisterResponse response1 = authControllerService.registerUser(registerRequest);

        Cookie cookie = createAuthorizationCookie(response1.getToken());
        response.addCookie(cookie);

        return new ResponseEntity<>(response1, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user. The request body contains login credentials (email and password).
     */
    @PostMapping("/login")
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user using email and password and returns a JWT token if valid."
    )
    public ResponseEntity<LoginResponse> loginUser(
            @Parameter(description = "User credentials for authentication", required = true)
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        LoginResponse response1 = authControllerService.login(loginRequest);

        Cookie cookie = createAuthorizationCookie(response1.getToken());
        response.addCookie(cookie);

        return new ResponseEntity<>(response1, HttpStatus.OK);
    }

    /**
     * Logs out the user by removing the authorization cookie.
     */
    @DeleteMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    cookie.setPath("/"); // Ensure the path matches the original cookie
                    response.addCookie(cookie);
                }
            }
            return new ResponseEntity<>("User has been logged out!!", HttpStatus.OK);
        }

        return new ResponseEntity<>("No Authorization cookie found.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Utility method to create an authorization cookie.
     *
     * @param token The JWT token to set in the cookie.
     * @return The created cookie with appropriate attributes.
     */
    private Cookie createAuthorizationCookie(String token) {
        Cookie cookie = new Cookie("Authorization", URLEncoder.encode("Bearer " + token, StandardCharsets.UTF_8));
        cookie.setMaxAge(expireTime);
        cookie.setSecure("prod".equals(environment)); // Secure cookie only in production
        cookie.setHttpOnly(true); // Protect against XSS
        cookie.setPath("/"); // Global cookie for the application
        return cookie;
    }
}
