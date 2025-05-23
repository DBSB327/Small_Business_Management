package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.CustomerRequest;
import com.example.smallbusinessmanagement.dto.CustomerSalesResponse;
import com.example.smallbusinessmanagement.model.Customer;
import com.example.smallbusinessmanagement.model.Sale;
import com.example.smallbusinessmanagement.repo.CustomerRepository;
import com.example.smallbusinessmanagement.repo.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public Customer createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        return customerRepository.save(customer);
    }

    public CustomerSalesResponse getCustomerSales(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Клиент не найден"));

        List<Sale> sales = saleRepository.findByCustomerId(customerId);
        BigDecimal totalSpent = sales.stream()
                .map(s -> s.getItems().stream()
                        .map(i -> i.getSellingPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CustomerSalesResponse(
                customer.getId(),
                customer.getFullName(),
                sales.size(),
                totalSpent
        );
    }
}
