package com.manish.rbac.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisterRequest {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> roles;

}
