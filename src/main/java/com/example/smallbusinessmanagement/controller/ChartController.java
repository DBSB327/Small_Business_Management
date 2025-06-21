package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.InventoryStatsDTO;
import com.example.smallbusinessmanagement.dto.PopularProductChartDTO;
import com.example.smallbusinessmanagement.dto.RecentSalesResponse;
import com.example.smallbusinessmanagement.dto.SalesChartDTO;
import com.example.smallbusinessmanagement.service.ChartService;
import com.example.smallbusinessmanagement.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;
    private final SaleService saleService;

    @GetMapping("/sales")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    public ResponseEntity<SalesChartDTO> getSalesChart(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        SalesChartDTO data = chartService.getSalesChartData(start, end);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/recent-sales")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    public ResponseEntity<RecentSalesResponse> getRecentSales() {
        RecentSalesResponse response = chartService.getRecentSales();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory-stats")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    public ResponseEntity<InventoryStatsDTO> getInventoryStats() {
        InventoryStatsDTO stats = chartService.getInventoryStats();
        return ResponseEntity.ok(stats);

    }

    @GetMapping("/popular-products")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    public List<PopularProductChartDTO> getPopularProductsChart(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return saleService.getTopSellingProductsChart(limit);
    }
}
