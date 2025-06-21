package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
}
