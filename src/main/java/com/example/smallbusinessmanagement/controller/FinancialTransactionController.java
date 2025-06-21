package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.FinancialTransactionResponse;
import com.example.smallbusinessmanagement.enums.TransactionType;
import com.example.smallbusinessmanagement.model.FinancialTransaction;
import com.example.smallbusinessmanagement.service.FinancialTransactionService;
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
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class FinancialTransactionController {
    private final FinancialTransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<List<FinancialTransactionResponse>> getTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) TransactionType type) {

        List<FinancialTransactionResponse> transactions = transactionService.getTransactionsByPeriod(start, end, type);
        return ResponseEntity.ok(transactions);
    }

}