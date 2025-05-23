package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.*;
import com.example.smallbusinessmanagement.exceptions.InsufficientStockException;
import com.example.smallbusinessmanagement.exceptions.InvalidDiscountException;
import com.example.smallbusinessmanagement.model.*;
import com.example.smallbusinessmanagement.repository.CustomerRepository;
import com.example.smallbusinessmanagement.repository.EmployeeRepository;
import com.example.smallbusinessmanagement.repository.ProductRepository;
import com.example.smallbusinessmanagement.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final FinancialTransactionService transactionService;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public SaleResponse createSale(SaleRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));
        Customer customer = getCustomerIfPresent(request.getCustomerId());

        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setEmployee(employee);
        sale.setCustomer(customer);
        sale.setDiscount(request.getDiscount());
        sale.setPaymentMethod(request.getPaymentMethod());

        List<SaleItem> saleItems = processSaleItems(request.getItems(), sale);
        sale.setItems(saleItems);

        BigDecimal total = calculateTotal(saleItems, request.getDiscount());
        Sale savedSale = saleRepository.save(sale);

        transactionService.createSaleTransaction(savedSale, total);
        return mapToResponse(savedSale, total);
    }

    private Customer getCustomerIfPresent(Long customerId) {
        if (customerId == null) return null;
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));
    }

    private List<SaleItem> processSaleItems(@NotEmpty List<SaleItemRequest> itemRequests, Sale sale) {
        List<SaleItem> saleItems = new ArrayList<>();

        for (SaleItemRequest itemRequest : itemRequests) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Недостаточно товара: " + product.getName());
            }

            BigDecimal price = itemRequest.getCustomPrice() != null
                    ? itemRequest.getCustomPrice()
                    : product.getSellingPrice();

            SaleItem item = new SaleItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setSellingPrice(price);
            item.setSale(sale);
            saleItems.add(item);

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);
        }

        return saleItems;
    }

    public BigDecimal calculateTotal(List<SaleItem> items, BigDecimal discount) {
        BigDecimal total = items.stream()
                .map(i -> i.getSellingPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            if (discount.compareTo(total) > 0) {
                throw new InvalidDiscountException("Скидка не может превышать сумму заказа");
            }
            total = total.subtract(discount);
        }

        return total;
    }


    private SaleResponse mapToResponse(Sale sale, BigDecimal total) {
        SaleResponse response = new SaleResponse();
        response.setId(sale.getId());
        response.setDate(sale.getDate());
        response.setTotal(total);
        response.setDiscount(sale.getDiscount());
        response.setPaymentMethod(sale.getPaymentMethod());

        if (sale.getCustomer() != null) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setId(sale.getCustomer().getId());
            customerInfo.setName(sale.getCustomer().getFullName());
            customerInfo.setPhone(sale.getCustomer().getPhone());
            response.setCustomer(customerInfo);
        }

        List<SaleItemResponse> itemResponses = new ArrayList<>();
        for (SaleItem item : sale.getItems()) {
            SaleItemResponse itemResponse = new SaleItemResponse();
            itemResponse.setProductName(item.getProduct().getName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPricePerUnit(item.getSellingPrice());
            itemResponse.setTotal(
                    item.getSellingPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            itemResponses.add(itemResponse);
        }
        response.setItems(itemResponses);

        return response;
    }
}
