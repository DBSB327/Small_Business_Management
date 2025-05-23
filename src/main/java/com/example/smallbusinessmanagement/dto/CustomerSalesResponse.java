package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CustomerSalesResponse {
    private Long id;
    private String fullName;
    private int totalPurchases;
    private BigDecimal totalSpent;
}