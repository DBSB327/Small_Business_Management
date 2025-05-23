package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.model.FinancialTransaction;
import com.example.smallbusinessmanagement.service.FinancialTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<FinancialTransaction>> getAllTransactions(@RequestParam(required = false) LocalDate start, @RequestParam(required = false) LocalDate end) {
        List<FinancialTransaction> transactions = transactionService.getTransactionsByPeriod(start, end);
        return ResponseEntity.ok(transactions);
    }
}