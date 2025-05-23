package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.SupplyItemRequest;
import com.example.smallbusinessmanagement.dto.SupplyItemResponse;
import com.example.smallbusinessmanagement.dto.SupplyRequest;
import com.example.smallbusinessmanagement.dto.SupplyResponse;
import com.example.smallbusinessmanagement.model.*;
import com.example.smallbusinessmanagement.repository.EmployeeRepository;
import com.example.smallbusinessmanagement.repository.ProductRepository;
import com.example.smallbusinessmanagement.repository.SupplierRepository;
import com.example.smallbusinessmanagement.repository.SupplyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final FinancialTransactionService transactionService;

    @Transactional
    public SupplyResponse createSupply(SupplyRequest request, Long employeeId) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Поставщик не найден"));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));

        Supply supply = new Supply();
        supply.setDate(LocalDateTime.now());
        supply.setSupplier(supplier);
        supply.setEmployee(employee);

        List<SupplyItem> supplyItems = processSupplyItems(request.getItems(), supply);
        supply.setItems(supplyItems);

        BigDecimal totalCost = calculateTotalCost(supplyItems);
        Supply savedSupply = supplyRepository.save(supply);

        transactionService.createSupplyTransaction(savedSupply, totalCost);
        return mapToResponse(savedSupply, totalCost);
    }

    private List<SupplyItem> processSupplyItems(List<SupplyItemRequest> items, Supply supply) {
        return items.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));

            SupplyItem supplyItem = new SupplyItem();
            supplyItem.setProduct(product);
            supplyItem.setQuantity(item.getQuantity());
            supplyItem.setPurchasePrice(item.getPurchasePrice());
            supplyItem.setSupply(supply);

            product.setStock(product.getStock() + item.getQuantity());
            product.setPurchasePrice(item.getPurchasePrice());
            productRepository.save(product);

            return supplyItem;
        }).toList();
    }

    private BigDecimal calculateTotalCost(List<SupplyItem> items) {
        return items.stream()
                .map(item -> item.getPurchasePrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private SupplyResponse mapToResponse(Supply supply, BigDecimal totalCost) {
        return new SupplyResponse(
                supply.getId(),
                supply.getDate(),
                supply.getSupplier().getName(),
                supply.getItems().stream()
                        .map(item -> new SupplyItemResponse(
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getPurchasePrice()))
                        .toList(),
                totalCost
        );
    }
}

