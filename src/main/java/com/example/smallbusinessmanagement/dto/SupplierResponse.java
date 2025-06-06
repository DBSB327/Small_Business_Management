package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String contactPhone;
    private String email;
}
