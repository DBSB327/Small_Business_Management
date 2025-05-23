package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private ProductCategory category;
    private Size size;
    private Color color;
    private BigDecimal sellingPrice;
    private int stock;
    private WarehouseResponse warehouse;

}