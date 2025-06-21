package com.example.smallbusinessmanagement.repository;

import com.example.smallbusinessmanagement.enums.TransactionType;
import com.example.smallbusinessmanagement.model.FinancialTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinancialTransactionRepository extends CrudRepository<FinancialTransaction, Long> {
    List<FinancialTransaction> findByDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<FinancialTransaction> findByDateBetweenAndType(LocalDateTime startDateTime, LocalDateTime endDateTime, TransactionType type);
}
