package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.InventoryStatsDTO;
import com.example.smallbusinessmanagement.dto.RecentSalesDTO;
import com.example.smallbusinessmanagement.dto.RecentSalesResponse;
import com.example.smallbusinessmanagement.dto.SalesChartDTO;
import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.repository.ProductRepository;
import com.example.smallbusinessmanagement.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChartService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SalesChartDTO getSalesChartData(LocalDate start, LocalDate end) {
        List<Object[]> rawData = saleRepository.getDailySalesSum(
                start.atStartOfDay(),
                end.atTime(23, 59, 59)
        );

        SalesChartDTO dto = new SalesChartDTO();
        dto.setLabels(new ArrayList<>());
        dto.setData(new ArrayList<>());

        for (Object[] row : rawData) {
            dto.getLabels().add(row[0].toString());
            dto.getData().add((BigDecimal) row[1]);
        }

        return dto;
    }

    public RecentSalesResponse getRecentSales() {
        RecentSalesResponse response = new RecentSalesResponse();

        Long totalSales = saleRepository.count();
        BigDecimal totalRevenue = saleRepository.sumTotalRevenue();

        response.setTotalSales(totalSales != null ? totalSales : 0L);
        response.setTotalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO);

        List<Sale> recentSales = saleRepository.findTop10ByOrderByDateDesc();
        response.setRecentSales(recentSales.stream()
                .map(sale -> RecentSalesDTO.builder()
                        .id(sale.getId())
                        .customerName(sale.getCustomer() != null ? sale.getCustomer().getFullName() : "Walk-in Customer")
                        .totalAmount(BigDecimal.valueOf(
                                sale.getItems().stream()
                                        .mapToDouble(item -> item.getQuantity() * item.getSellingPrice().doubleValue())
                                        .sum()))
                        .saleDate(sale.getDate())
                        .build()
                )
                .collect(Collectors.toList()));

        return response;
    }

    public InventoryStatsDTO getInventoryStats() {
        InventoryStatsDTO stats = new InventoryStatsDTO();

        stats.setTotalProducts(productRepository.count());

        Integer totalStock = productRepository.sumStock();
        stats.setTotalItemsInStock(totalStock != null ? totalStock : 0);

        stats.setLowStockItems(productRepository.countByStockLessThan(5));

        stats.setOutOfStockItems(productRepository.countByStock(0));

        BigDecimal inventoryValue = productRepository.calculateInventoryValue();
        stats.setInventoryValue(inventoryValue != null ? inventoryValue : BigDecimal.ZERO);

        return stats;
    }
}

