package com.manish.rbac.dto;

import lombok.Builder;
import lombok.Data;


/**
 * DTO class for user registration request.
 * This class holds the necessary details provided by the user during the registration process.
 */
@Data
@Builder
public class RegisterRequest {
    private String id;
    private String name;
    private String email;
    private String password;
    private String roles;
}
