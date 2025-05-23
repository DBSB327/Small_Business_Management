package com.example.smallbusinessmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.example.smallbusinessmanagement.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleResponse {
    Long id;
    LocalDateTime date;
    CustomerInfo customer;
    List<SaleItemResponse> items;
    BigDecimal total;
    BigDecimal discount;
    PaymentMethod paymentMethod;
}
