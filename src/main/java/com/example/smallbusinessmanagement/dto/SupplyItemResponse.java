package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SupplyItemResponse {
    String productName;
    int quantity;
    BigDecimal purchasePrice;
}