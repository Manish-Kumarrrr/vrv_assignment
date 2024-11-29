package com.manish.rbac.service;

import com.manish.rbac.dto.LoginRequest;
import com.manish.rbac.dto.LoginResponse;
import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;

public interface AuthControllerService {
    RegisterResponse registerUser(RegisterRequest registerRequest);


    LoginResponse login(LoginRequest loginRequest);
}
