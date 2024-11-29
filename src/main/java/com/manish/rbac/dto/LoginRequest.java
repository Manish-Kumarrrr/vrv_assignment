package com.manish.rbac.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO class for capturing login request details.
 * This class is used to receive the login request containing user credentials.
 */
@Data
@Builder
public class LoginRequest {

    private String id;
    private String password;
}
