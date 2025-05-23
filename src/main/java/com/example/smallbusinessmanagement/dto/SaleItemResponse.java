package com.example.smallbusinessmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class SaleItemResponse {
    String productName;
    int quantity;
    BigDecimal pricePerUnit;
    BigDecimal total;
}