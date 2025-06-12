package com.example.smallbusinessmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryStatsDTO {
    private Long totalProducts;
    private Integer totalItemsInStock;
    private Long lowStockItems;
    private Long outOfStockItems;
    private BigDecimal inventoryValue;
}