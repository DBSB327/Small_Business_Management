package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductStockInfo {
    private Long id;
    private String name;
    private int stock;
    private BigDecimal sellingPrice;
}
