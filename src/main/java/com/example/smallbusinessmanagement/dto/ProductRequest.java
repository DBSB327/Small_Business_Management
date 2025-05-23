package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    @NotBlank
    private String name;

    @NotNull
    private ProductCategory category;

    @NotNull
    private Size size;

    @NotNull
    private Color color;

    @Positive
    private BigDecimal purchasePrice;

    @Positive
    private BigDecimal sellingPrice;
}