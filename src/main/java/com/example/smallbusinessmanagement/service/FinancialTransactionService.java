package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.enums.TransactionType;
import com.example.smallbusinessmanagement.model.FinancialTransaction;
import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.model.Supply;
import com.example.smallbusinessmanagement.repository.FinancialTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialTransactionService {
    private final FinancialTransactionRepository transactionRepository;

    public void createSupplyTransaction(Supply supply, BigDecimal amount) {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setType(TransactionType.EXPENSE);
        transaction.setAmount(amount);
        transaction.setDescription("Поставка от " + supply.getSupplier().getName());
        transaction.setSupply(supply);
        transaction.setDate(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    public void createSaleTransaction(Sale sale, BigDecimal total) {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setType(TransactionType.INCOME);
        transaction.setAmount(total);
        transaction.setDescription("Продажа #" + sale.getId());
        transaction.setSale(sale);
        transaction.setDate(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    public List<FinancialTransaction> getTransactionsByPeriod(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.plusDays(1).atStartOfDay().minusSeconds(1);
        return transactionRepository.findByDateBetween(startDateTime, endDateTime);
        }
    }


