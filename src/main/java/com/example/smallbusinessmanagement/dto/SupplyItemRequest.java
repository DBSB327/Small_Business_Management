package com.example.smallbusinessmanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class SupplyItemRequest {
    @NotNull
    Long productId;
    @Min(1)
    int quantity;
    @DecimalMin("0.01")
    BigDecimal purchasePrice;
}