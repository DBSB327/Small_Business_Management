package com.example.smallbusinessmanagement.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class SaleItemRequest{
    @NotNull Long productId;
    @Min(1) int quantity;
    @DecimalMin("0.01") @Nullable BigDecimal customPrice; // Переопределение цены (опционально)
}