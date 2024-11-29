package com.manish.rbac.controller;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;
import com.manish.rbac.service.AuthControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles all authentication-related endpoints such as user registration and login.
 */
@RestController
@RequestMapping("/v1/auth") // Base URL for authentication-related endpoints
@RequiredArgsConstructor // Lombok annotation to automatically generate a constructor for the final fields
public class AuthController {

    private final AuthControllerService authControllerService; // Service layer for authentication logic

    /**
     * Registers a new user. The request body contains the user's registration details.
     *
     * @param registerRequest The request body containing user registration data
     * @return ResponseEntity containing the registration response and HTTP status code
     */
    @PostMapping("/register") // Endpoint for registering a new user
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Calls the service to register the user and returns the response with a CREATED status (201)
        return new ResponseEntity<>(authControllerService.registerUser(registerRequest), HttpStatus.CREATED);
    }

    /**
     * Authenticates a user. The request body contains login credentials (email and password).
     *
     * @param loginRequest The request body containing user login data
     * @return ResponseEntity containing the login response and HTTP status code
     */
    @GetMapping("/login") // Endpoint for user login
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        // Calls the service to authenticate the user and returns the response with an OK status (200)
        return new ResponseEntity<>(authControllerService.login(loginRequest), HttpStatus.OK);
    }
}
