package com.example.smallbusinessmanagement.dto.securityDTO;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
