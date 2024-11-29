package com.manish.rbac.service.impl;

import com.manish.rbac.controller.ResourceController;
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
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthControllerServiceImpl implements AuthControllerService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(ResourceController.class);
    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        User inputUser=User.builder()
                .id(registerRequest.getId())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .roles(registerRequest.getRoles())
                .build();
        if (userRepository.existsById(inputUser.getId())) {
//            logger.warning("User already exists with id: " + inputUser.getId());
            throw new UserAlreadyExistsException("User already exists with id: " + inputUser.getId());
        }
        User savedUser=userRepository.save(inputUser);
        String token= jwtUtil.generateToken(savedUser);
        return RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .roles(savedUser.getRoles())
                .token(token)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user=userRepository.findById(loginRequest.getId()).orElseThrow(()->new UserNotFoundException("User doesn't exist with id: "+loginRequest.getId()));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
        throw new UserNotFoundException("Wrong Credential!!");
        }
        String token= jwtUtil.generateToken(user);
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .build();
    }

}
