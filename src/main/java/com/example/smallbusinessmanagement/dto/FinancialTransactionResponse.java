package com.example.smallbusinessmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FinancialTransactionResponse {
    private Long id;
    private LocalDateTime date;
    private String type;
    private BigDecimal amount;
    private String description;
    private SaleInfo sale;
}
