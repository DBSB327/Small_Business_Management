package com.example.smallbusinessmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SaleInfo {
    private Long id;
    private LocalDateTime date;
    private CustomerInfo customer;

}
