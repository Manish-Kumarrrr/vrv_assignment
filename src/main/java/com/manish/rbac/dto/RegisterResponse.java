package com.manish.rbac.dto;


import lombok.Builder;
import lombok.Data;



/**
 * DTO class for user registration response.
 * This class holds the necessary details provided to the user during the registration process.
 */
@Data
@Builder
public class RegisterResponse {
    private String id;
    private String name;
    private String email;
    private String roles;
    private String token;

}
