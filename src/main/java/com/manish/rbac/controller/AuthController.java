package com.manish.rbac.controller;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;
import com.manish.rbac.exception.UserAlreadyExistsException;
import com.manish.rbac.service.AuthControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthControllerService authControllerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authControllerService.registerUser(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authControllerService.login(loginRequest), HttpStatus.OK);
    }





}
