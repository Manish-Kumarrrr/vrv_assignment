package com.manish.rbac.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisterResponse {
    private String id;
    private String name;
    private String email;
    private List<String> roles;
    private String token;

}
