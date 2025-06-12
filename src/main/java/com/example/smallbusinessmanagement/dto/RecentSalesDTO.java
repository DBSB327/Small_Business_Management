package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class RecentSalesDTO {
    private Long id;
    private String customerName;
    private BigDecimal totalAmount;
    private LocalDateTime saleDate;
}
