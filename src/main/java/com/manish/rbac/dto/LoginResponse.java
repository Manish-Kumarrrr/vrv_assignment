package com.manish.rbac.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO class for sending the login response details.
 * This class contains the user identifier and the JWT token returned after successful login.
 */
@Data
@Builder
public class LoginResponse {

    private String id;

    /**
     * The JWT token generated for the user upon successful authentication.
     * This token is used for subsequent requests to authenticate and authorize the user.
     */
    private String token;
}
