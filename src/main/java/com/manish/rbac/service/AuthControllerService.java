package com.manish.rbac.service;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;

/**
 * Interface for authentication services. This defines the methods
 * related to user registration and login processes.
 */
public interface AuthControllerService {

    /**
     * Registers a new user based on the provided register request details.
     *
     * @param registerRequest The details for the user to be registered.
     * @return A RegisterResponse containing the result of the registration process.
     */
    RegisterResponse registerUser(RegisterRequest registerRequest);

    /**
     * Authenticates a user based on the provided login request details.
     *
     * @param loginRequest The login credentials (id and password) of the user.
     * @return A LoginResponse containing the user id and JWT token.
     */
    LoginResponse login(LoginRequest loginRequest);
}
