package com.manish.rbac.service;

import com.manish.rbac.dto.RegisterRequest;
import com.manish.rbac.dto.RegisterResponse;

public interface AuthControllerService {
    RegisterResponse registerUser(RegisterRequest registerRequest);

}
