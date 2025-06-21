package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.enums.TransactionType;
import com.example.smallbusinessmanagement.service.SaleReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<byte[]> getSalesExcelReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) throws IOException {
        byte[] report = saleReportService.generateSalesExcelReport(start, end);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=sales-report.xlsx")
                .body(report);
    }

    @GetMapping(value = "/transactions/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<byte[]> getFinancialTransactionsExcelReport (
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) TransactionType type
    ) throws IOException {
        byte[] report = saleReportService.generateFinancialTransactionsExcelReport(
                start != null ? start : LocalDate.of(2000, 1, 1),
                end != null ? end : LocalDate.now(),
                type
        );
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=financial-transactions-report.xlsx")
                .body(report);
    }
}
