package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.service.SaleReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class SaleReportController {

    private final SaleReportService saleReportService;

    @GetMapping(value = "/sales/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> getSalesExcelReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) throws IOException {
        byte[] report = saleReportService.generateSalesExcelReport(start, end);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=sales-report.xlsx")
                .body(report);


    }
}
