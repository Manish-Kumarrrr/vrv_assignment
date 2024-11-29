package com.manish.rbac.service.impl;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;
import com.manish.rbac.exception.UserAlreadyExistsException;
import com.manish.rbac.exception.UserNotFoundException;
import com.manish.rbac.model.User;
import com.manish.rbac.repository.UserRepository;
import com.manish.rbac.service.AuthControllerService;
import com.manish.rbac.utilis.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Automatically generates a constructor to inject dependencies
public class AuthControllerServiceImpl implements AuthControllerService {

    private final UserRepository userRepository; // Repository to interact with the MongoDB database
    private final PasswordEncoder passwordEncoder; // For encoding and verifying user passwords
    private final JwtUtil jwtUtil; // Utility to generate and validate JWT tokens

    /**
     * Register a new user.
     *
     * @param registerRequest The user details for registration.
     * @return RegisterResponse containing user information and JWT token.
     */
    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        // Create a new User object from the registerRequest
        User inputUser = User.builder()
                .id(registerRequest.getId()) // Set the user ID
                .name(registerRequest.getName()) // Set the user name
                .password(passwordEncoder.encode(registerRequest.getPassword())) // Encode the password
                .email(registerRequest.getEmail()) // Set the user email
                .roles(registerRequest.getRoles()) // Set the user roles (USER, ADMIN, etc.)
                .build();

        // Check if a user with the same ID already exists in the database
        if (userRepository.existsById(inputUser.getId())) {
            throw new UserAlreadyExistsException("User already exists with id: " + inputUser.getId());
        }

        // Save the user to the database
        User savedUser = userRepository.save(inputUser);

        // Generate a JWT token for the saved user
        String token = jwtUtil.generateToken(savedUser);

        // Return a response with user details and the generated token
        return RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .roles(savedUser.getRoles())
                .token(token)
                .build();
    }

    /**
     * Handle user login.
     *
     * @param loginRequest The user's login credentials.
     * @return LoginResponse containing the user's ID and JWT token.
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // Retrieve the user by ID, throw exception if user not found
        User user = userRepository.findById(loginRequest.getId())
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist with id: " + loginRequest.getId()));

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Wrong Credential!!");
        }

        // Generate a JWT token for the authenticated user
        String token = jwtUtil.generateToken(user);

        // Return a response with the user's ID and the generated token
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .build();
    }
}
