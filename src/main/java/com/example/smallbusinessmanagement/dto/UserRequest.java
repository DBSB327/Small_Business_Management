package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private String fullName;
    private String phone;
}
