package com.manish.rbac.controller;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;
import com.manish.rbac.service.AuthControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles all authentication-related endpoints such as user registration and login.
 */
@RestController
@RequestMapping("/v1/auth") // Base URL for authentication-related endpoints
@RequiredArgsConstructor // Lombok's annotation to automatically generate a constructor for the final fields
@Tag(name = "Authentication API", description = "APIs for user authentication and authorization")
public class AuthController {

    private final AuthControllerService authControllerService; // Service layer for authentication logic

    /**
     * Registers a new user. The request body contains the user's registration details.
     *
     * @param registerRequest The request body containing user registration data.
     * @return ResponseEntity containing the registration response and HTTP status code.
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user and stores the information in the database."
    )
    public ResponseEntity<RegisterResponse> registerUser(
            @Parameter(description = "Details of the user to register", required = true)
            @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = authControllerService.registerUser(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user. The request body contains login credentials (email and password).
     *
     * @param loginRequest The request body containing user login data.
     * @return ResponseEntity containing the login response and HTTP status code.
     */
    @PostMapping("/login")
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates a user using email and password and returns a JWT token if valid."
    )
    public ResponseEntity<LoginResponse> loginUser(
            @Parameter(description = "User credentials for authentication", required = true)
            @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authControllerService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
