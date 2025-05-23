package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
    private UserRole role;
}
