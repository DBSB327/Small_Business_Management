package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.SalesChartDTO;
import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.repo.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {
    private final SaleRepository saleRepository;

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
}

