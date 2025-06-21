package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PopularProductChartDTO {
    private Long id;
    private String name;
    private ProductCategory category;
    private Size size;
    private Color color;
    private Long quantitySold;
    private BigDecimal totalRevenue;
}
