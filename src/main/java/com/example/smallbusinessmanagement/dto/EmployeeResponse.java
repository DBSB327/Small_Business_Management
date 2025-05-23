package com.example.smallbusinessmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {
    private Long id;
    private String fullName;
    private String position;
    private Long userId;
}
