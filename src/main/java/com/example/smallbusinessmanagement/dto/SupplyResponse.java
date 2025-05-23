package com.example.smallbusinessmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SupplyResponse {
    Long id;
    LocalDateTime date;
    String supplierName;
    List<SupplyItemResponse> items;
    BigDecimal totalCost;


}
