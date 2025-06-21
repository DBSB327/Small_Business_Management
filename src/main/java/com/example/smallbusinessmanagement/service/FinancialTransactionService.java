package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.CustomerInfo;
import com.example.smallbusinessmanagement.dto.FinancialTransactionResponse;
import com.example.smallbusinessmanagement.dto.SaleInfo;
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
import java.util.stream.Collectors;

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

    public List<FinancialTransactionResponse> getTransactionsByPeriod(LocalDate start, LocalDate end, TransactionType type) {
        if (start == null) {
            start = LocalDate.of(2000, 1, 1);
        }
        if (end == null) {
            end = LocalDate.now();
        }
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.plusDays(1).atStartOfDay().minusSeconds(1);

        List<FinancialTransaction> transactions;

        if (type != null) {
            transactions = transactionRepository.findByDateBetweenAndType(startDateTime, endDateTime, type);
        } else {
            transactions = transactionRepository.findByDateBetween(startDateTime, endDateTime);
        }

        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private FinancialTransactionResponse convertToDto(FinancialTransaction tx) {
        FinancialTransactionResponse dto = new FinancialTransactionResponse();
        dto.setId(tx.getId());
        dto.setDate(tx.getDate());
        dto.setType(tx.getType().name());
        dto.setAmount(tx.getAmount());
        dto.setDescription(tx.getDescription());

        if (tx.getSale() != null) {
            SaleInfo saleDto = new SaleInfo();
            saleDto.setId(tx.getSale().getId());
            saleDto.setDate(tx.getSale().getDate());

            if (tx.getSale().getCustomer() != null) {
                CustomerInfo customerDto = new CustomerInfo();
                customerDto.setId(tx.getSale().getCustomer().getId());
                customerDto.setFullName(tx.getSale().getCustomer().getFullName());
                customerDto.setPhone(tx.getSale().getCustomer().getPhone());
                saleDto.setCustomer(customerDto);
            }
            dto.setSale(saleDto);
        }

        return dto;
    }
}


