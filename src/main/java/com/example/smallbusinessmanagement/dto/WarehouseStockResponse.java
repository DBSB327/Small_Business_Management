package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseStockResponse {
    private Long id;
    private String name;
    private List<ProductStockInfo> products;
}
