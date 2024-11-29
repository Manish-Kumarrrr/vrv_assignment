package com.manish.rbac.controller;

import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;
import com.manish.rbac.exception.UserAlreadyExistsException;
import com.manish.rbac.service.AuthControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthControllerService authControllerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authControllerService.registerUser(registerRequest), HttpStatus.CREATED);
    }



}
