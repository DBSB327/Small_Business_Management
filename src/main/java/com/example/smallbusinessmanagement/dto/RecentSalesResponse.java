package com.example.smallbusinessmanagement.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecentSalesResponse {
    private Long totalSales;
    private BigDecimal totalRevenue;
    private List<RecentSalesDTO> recentSales;
}