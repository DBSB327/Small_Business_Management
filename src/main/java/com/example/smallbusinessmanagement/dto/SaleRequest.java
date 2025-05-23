package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.PaymentMethod;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class SaleRequest {
    @Nullable
    Long customerId;
    @NotEmpty
    List<SaleItemRequest> items;
    @DecimalMin("0.0")
    BigDecimal discount;
    @NotNull
    PaymentMethod paymentMethod;

    @NotNull
    private Long employeeId;
}